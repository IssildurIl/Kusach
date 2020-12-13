package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import ru.sfedu.groupappcontrol.utils.EmployeeConverter;
import ru.sfedu.groupappcontrol.utils.TaskListConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Root(name = "Project")
public class Project implements Serializable {
  @Attribute
  @CsvBindByName
  private long id;
  @Element
  @CsvBindByName
  private String title;
  @Element
  @CsvBindByName
  private String takeIntoDevelopment;
  @ElementList
  @CsvCustomBindByName(converter = TaskListConverter.class)
  private List<Task> task;

  public Project () { };

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setTitle (String newVar) {
    title = newVar;
  }

  public String getTitle () {
    return title;
  }

  public void setTakeIntoDevelopment (String newVar) {
    takeIntoDevelopment = newVar;
  }

  public String getTakeIntoDevelopment () {
    return takeIntoDevelopment;
  }

  public List<Task> getTask() {
    return task;
  }

  public void setTask(List<Task> task) {
    this.task = task;
  }

  @Override
  public String toString() {
    return "Project{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", takeIntoDevelopment='" + takeIntoDevelopment + '\'' +
            ", task=" + task +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Project project = (Project) o;
    return Objects.equals(id, project.id) &&
            Objects.equals(title, project.title) &&
            Objects.equals(takeIntoDevelopment, project.takeIntoDevelopment) &&
            Objects.equals(task, project.task);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, takeIntoDevelopment, task);
  }
}
