package dmsystem.service;

import dmsystem.util.FileUtility;
import org.kamranzafar.jtar.TarEntry;
import org.kamranzafar.jtar.TarOutputStream;

import java.io.*;
import java.util.Properties;

/**
 * Created by justinyang on 14-1-6.
 */
public class BackupServiceImpl implements BackupService {
    public static void main(String[] args) {
        BackupServiceImpl backupService = new BackupServiceImpl();
        backupService.exportDataBase();
    }

    @Override
    public String export() {
        String dataBaseExportPath = this.exportDataBase();

        if (dataBaseExportPath == null) {
            System.err.println("Fail to back up database");
            return null;
        }

        String attachmentExportPath = this.exportAttachments();
        if (attachmentExportPath == null) {
            System.err.println("Fail to back up attachments");
            return null;
        }

        return this._combine(dataBaseExportPath, attachmentExportPath);
    }

    private String _combine(String databaseExportPath, String attachmentExportPath) {
        String exportPath = FileUtility.getCompisiteBackupFilePath();
        File exportFile = new File(exportPath);
        File parent = exportFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        TarOutputStream out = null;
        BufferedInputStream origin = null;
        try {
            FileOutputStream dest = new FileOutputStream(exportFile);
            out = new TarOutputStream( new BufferedOutputStream( dest ) );

            File[] filesToTar=new File[2];
            filesToTar[0]=new File(databaseExportPath);
            filesToTar[1]=new File(attachmentExportPath);
            for(File f:filesToTar){
                out.putNextEntry(new TarEntry(f, f.getName()));
                origin = new BufferedInputStream(new FileInputStream( f ));
                int count;
                byte data[] = new byte[2048];

                while((count = origin.read(data)) != -1) {
                    out.write(data, 0, count);
                }

                out.flush();
                origin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

            // Marks Exception
            exportPath = null;
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != origin) {
                try {
                    origin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return exportPath;
    }

    @Override
    public String exportAttachments() {
        String exportPath = FileUtility.getAttachmentBackupFilePath();
        File exportFile = new File(exportPath);
        File parent = exportFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        TarOutputStream out = null;
        BufferedInputStream origin = null;
        try {
            FileOutputStream dest = new FileOutputStream(exportFile);
            out = new TarOutputStream( new BufferedOutputStream( dest ) );

            String fileDirectoryPath = FileUtility.getUploadResourceDirectoryPath();
            File fileDirectory = new File(fileDirectoryPath);
            File[] filesToTar = fileDirectory.listFiles();
            for(File f:filesToTar){
                out.putNextEntry(new TarEntry(f, f.getName()));
                origin = new BufferedInputStream(new FileInputStream( f ));
                int count;
                byte data[] = new byte[2048];

                while((count = origin.read(data)) != -1) {
                    out.write(data, 0, count);
                }

                out.flush();
                origin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

            // Marks Exception
            exportFile = null;
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != origin) {
                try {
                    origin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return exportPath;
    }

    @Override
    public String exportDataBase() {
        String exportPath = FileUtility.getDatabaseBackupFilePath();
        File exportFile = new File(exportPath);
        File parent = exportFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            String backupShellPath = FileUtility.getRootDiretoryPath() + File.separator + "WEB-INF" + File.separator + "backup.sh";
            ProcessBuilder pb = new ProcessBuilder(backupShellPath);
            Process p = pb.start();
            bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(exportFile), "UTF-8"));
            String line;
            String newLineSeparator = System.getProperty("line.separator");
            while ( (line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line + newLineSeparator);
            }
        } catch (Exception e) {
            e.printStackTrace();

            // Marks exception
            exportFile = null;
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return exportPath;
    }

    public static void importSql(String importPath, Properties properties) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        //因为在命令窗口进行mysql数据库的导入一般分三步走，所以所执行的命令将以字符串数组的形式出现
        String cmdarray[] = getImportCommand(importPath, properties);//根据属性文件的配置获取数据库导入所需的命令，组成一个数组
        //runtime.exec(cmdarray);//这里也是简单的直接抛出异常
        Process process = runtime.exec(cmdarray[0]);
        //执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口
        //进程执行后面的代码
        OutputStream os = process.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        //命令1和命令2要放在一起执行
        writer.write(cmdarray[1] + "\r\n" + cmdarray[2]);
        writer.flush();
        writer.close();
        os.close();
    }

    private static String[] getImportCommand(String importPath, Properties properties) {
        String username = properties.getProperty("jdbc.username");//用户名
        String password = properties.getProperty("jdbc.password");//密码
        String host = properties.getProperty("jdbc.host");//导入的目标数据库所在的主机
        String port = properties.getProperty("jdbc.port");//使用的端口号
        String importDatabaseName = properties.getProperty("jdbc.importDatabaseName");//导入的目标数据库的名称
        //第一步，获取登录命令语句
        String loginCommand = new StringBuffer().append("mysql -u").append(username).append(" -p").append(password).append(" -h").append(host)
                .append(" -P").append(port).toString();
        //第二步，获取切换数据库到目标数据库的命令语句
        String switchCommand = new StringBuffer("use ").append(importDatabaseName).toString();
        //第三步，获取导入的命令语句
        String importCommand = new StringBuffer("source ").append(importPath).toString();
        //需要返回的命令语句数组
        String[] commands = new String[] {loginCommand, switchCommand, importCommand};
        return commands;
    }
}
