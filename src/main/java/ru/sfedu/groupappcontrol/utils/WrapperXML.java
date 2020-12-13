package ru.sfedu.groupappcontrol.utils;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

@Root(name = "main")
public class WrapperXML<T> {
    @ElementList(name= "WrapperName", inline = true,required = false)
    public List<T> list;

    public WrapperXML(){ }

    public WrapperXML(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
