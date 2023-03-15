package com.paya.paragon.base.commonClass;


import java.io.File;

public class PairedValues {
    private int type=0;
    public static final int TYPE_TEXT=101,TYPE_FILE=102;
    private File file;
    private String key,text;
    public PairedValues(){}
    public PairedValues(String key,String value){
        setKey(key);
        setText(value);
    }
    public PairedValues(String key,File value){
        setKey(key);
        setFile(value);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        setType(TYPE_FILE);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        setType(TYPE_TEXT);
    }

    @Override
    public String toString() {
        if(getType()==TYPE_FILE)
            return getKey()+" "+getFile()+" ";
        else
            return getKey()+" "+getText()+" ";
    }
}
