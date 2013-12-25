package dmsystem.entity;

/**
 * Created by justinyang on 13-12-25.
 */
public class ExtraPropertyWrapper {
    private Integer extraPropertyId;
    private String extraPropertyName;
    private String extraPropertyValue;

    public Integer getExtraPropertyId() {
        return extraPropertyId;
    }

    public void setExtraPropertyId(Integer extraPropertyId) {
        this.extraPropertyId = extraPropertyId;
    }

    public String getExtraPropertyName() {
        return extraPropertyName;
    }

    public void setExtraPropertyName(String extraPropertyName) {
        this.extraPropertyName = extraPropertyName;
    }

    public String getExtraPropertyValue() {
        return extraPropertyValue;
    }

    public void setExtraPropertyValue(String extraPropertyValue) {
        this.extraPropertyValue = extraPropertyValue;
    }
}
