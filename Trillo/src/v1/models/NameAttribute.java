package v1.models;

public class NameAttribute extends Attribute{
    private String fieldValue;
    public NameAttribute(String fieldName, String fieldValue){
        super(fieldName);
        this.fieldValue = fieldValue;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
