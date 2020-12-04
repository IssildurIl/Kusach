package ru.sfedu.groupappcontrol.models;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import ru.sfedu.groupappcontrol.utils.EmployeeConverter;
import ru.sfedu.groupappcontrol.utils.TaskListConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class Project extends BaseClass implements Serializable {
  @CsvBindByName
  private String title;
  @CsvBindByName
  private String takeIntoDevelopment;
  @CsvCustomBindByName(converter = TaskListConverter.class)
  private List<Task> task;

  public Project () { };

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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Project project = (Project) o;
    return Objects.equals(title, project.title) &&
            Objects.equals(takeIntoDevelopment, project.takeIntoDevelopment) &&
            Objects.equals(task, project.task);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, takeIntoDevelopment, task);
  }

  @Override
  public String toString() {
    return "Project{" +
            "title='" + title + '\'' +
            ", takeIntoDevelopment='" + takeIntoDevelopment + '\'' +
            ", task=" + task +
            '}';
  }
}
