package dmsystem.action;

import com.opensymphony.xwork2.ActionSupport;
import dmsystem.service.BackupService;

/**
 * Created by justinyang on 14-1-6.
 */
public class BackupAction extends ActionSupport {

    private BackupService backupService;

    public void setBackupService(BackupService backupService) {
        this.backupService = backupService;
    }

    public String backup() {
        String exportPath = this.backupService.export();
        if (exportPath != null) {
            return SUCCESS;
        } else {
            return ERROR;
        }
    }
}
