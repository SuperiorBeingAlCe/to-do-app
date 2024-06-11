package todoapp.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Task {

 private int id;
 private String taskName;
 private String description;
 private Date date;
 private boolean checked;
 
 public Task() {
	
}
 
 public static Task buildTask(int id, String taskName, String description, Date date, boolean checked) {
	 Task task = new Task();
	 
	task.id = id;
	task.taskName = taskName;
	task.description = description;
	task.date = date;
	task.checked = checked;
	 
	return task;
	 
 }

public int getId() {
	return id;
}


public Date getDate() {
	return date;
}

public void setDate(Date date) {
	this.date = date;
}

public void setId(int id) {
	this.id = id;
}

public String getTaskName() {
	return taskName;
}

public void setTaskName(String taskName) {
	this.taskName = taskName;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public boolean isChecked() {
	return checked;
}

public void setChecked(boolean checked) {
	this.checked = checked;
}
	
@Override
public String toString() {
	
	    String dateString = date != null ? new SimpleDateFormat("yyyy-MM-dd").format(date) : "";
    
    return id + ". " + "Görev" +
             taskName + " | " +
              description + " görev son tarih: " +
             dateString
             
               ;
            
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return id == task.id &&
            checked == task.checked &&
            Objects.equals(taskName, task.taskName) &&
            Objects.equals(description, task.description);
}

public String getDateString() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
}


@Override
public int hashCode() {
    return Objects.hash(id, taskName, description, checked);
}
}
