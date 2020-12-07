//package ru.sfedu.groupappcontrol.api;
//
//import com.opencsv.bean.StatefulBeanToCsv;
//import com.opencsv.bean.StatefulBeanToCsvBuilder;
//import com.opencsv.exceptions.CsvDataTypeMismatchException;
//import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;
//import ru.sfedu.groupappcontrol.models.Result;
//import ru.sfedu.groupappcontrol.models.*;
//import ru.sfedu.groupappcontrol.models.constants.Constants;
//import ru.sfedu.groupappcontrol.models.constants.WrapperXML;
//import ru.sfedu.groupappcontrol.models.enums.*;
//import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Complete;
//import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Fail;
//
//public class DataProviderXML implements DataProvider {
//    private final String PATH = ConfigurationUtil.getConfigurationEntry("XML_PATH");
//
//    private final String FILE_EXTENSION_XML ="FILE_EXTENSION_XML";
//
//    private Logger log = LogManager.getLogger(DataProviderXML.class);
//
//    public DataProviderXML() throws IOException {
//    }
//
//    public void createDirectories(String pathString) throws IOException {
//        Path path = Paths.get(pathString);
//        Files.createDirectories(path);
//    }
//
//    public String getPath(Class cl) throws IOException {
//        return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION_XML);
//    }
//
//
//    public void createFile(String path) throws IOException {
//        File file = new File(path);
//        if (!file.exists()) {
//            Path dirPath = Paths.get(PATH);
//            Files.createDirectories(dirPath);
//            file.createNewFile();
//        }
//    }
//
//    public <T extends BaseClass> Result<T> getByID(Class cl, int id) throws Exception {
//        Result list = this.select(cl);
//        if (list.getStatus() == Fail) {
//            return list;
//        }
//        List<T> listRes = (List<T>) list.getData();
//        try {
//            T element = listRes.stream().filter(el -> el.getId() == id).findFirst().get();
//            return new Result(Complete, Constants.IS_INSERTED, element);
//        } catch (NoSuchElementException e) {
//            log.error("There is no element with this id");
//            return new Result(Fail, Constants.IS_FAILED, null);
//        }
//    }
//
//
//
//
//    public <T extends BaseClass> Result<T> insert(Class cl, List<T> list, boolean append) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
//        try {
//            String path = getPath(cl);
//            createFile(path);
//            List<T> oldList = (List<T>) this.select(cl).getData();
//            if (append) {
//                if (oldList != null && oldList.size() > 0) {
//                    int id = (int) list.get(0).getId();
//                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
//                        return new Result<>(Fail, Constants.IS_FAILED, null);
//                    }
//                    list = Stream
//                            .concat(list.stream(), oldList.stream())
//                            .collect(Collectors.toList());
//                }
//            }
//            FileWriter writer = new FileWriter(path);
//            Serializer serializer = new Persister();
//            WrapperXML<T> xml = new WrapperXML<T>();
//            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
//                    .withApplyQuotesToAll(false)
//                    .build();
//            xml.setList(list);
//            serializer.write(xml, writer);
//            writer.close();
//        } catch (IndexOutOfBoundsException ex) {
//            return new Result<>(Fail, Constants.IS_EMPTY, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new Result(Complete, Constants.IS_INSERTED, list);
//    }
//
//    public <T extends BaseClass> Result<T> delete(Class cl, int id) throws Exception {
//        Result list = select(cl);
//        List<T> listData = (List<T>) list.getData();
//        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
//        insert(cl, listData, false);
//        return new Result(Complete, Constants.IS_INSERTED, listData);
//    }
//
//
//
//    public <T extends BaseClass> Result<T> update(Class cl, int id) throws Exception {
//        delete(cl, id);
//        List<T> list = new ArrayList<T>();
//        list.addAll(list);
//        insert(cl, list, true);
//        return new Result(Complete, Constants.IS_INSERTED, list);
//    }
//
//
//    public <T> Result<T> select(Class cl) throws Exception {
//        String path = getPath(cl);
//        FileReader file = new FileReader(path);
//        Serializer serializer = new Persister();
//        WrapperXML xml = serializer.read(WrapperXML.class, file);
//        return new Result(Complete, Constants.IS_INSERTED, xml.list);
//    }
//
//    @Override
//    public Result changeProfileInfo(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result changeTaskStatus(long id, String status) {
//        return null;
//    }
//
//    @Override
//    public Result writeComment(long id, String comment) {
//        return null;
//    }
//
//
//    @Override
//    public Result getUserInfoList(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskList(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskInfo(long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTask(long userId, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result calculateTaskCost(Task task) {
//        return null;
//    }
//
//
//    @Override
//    public Result getProjectStatistic(long userId) {
//        return null;
//    }
//
//    @Override
//    public Result getProject(long userId, long projectId) {
//        return null;
//    }
//
//    @Override
//    public Result calculateProjectCost(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result calculateProjectTime(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result createTask(String taskDescription, Double money, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String deadline, String lastUpdate, TaskTypes taskType) {
//        return null;
//    }
//
//    @Override
//    public Result createTask(String taskDescription, Double money, String deadline, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String lastUpdate, TaskTypes taskType, DeveloperTaskType devTaskType, String developerComments) {
//        return null;
//    }
//
//    @Override
//    public Result createTask(String taskDescription, Double money, String deadline, Employee scrumMaster, TypeOfCompletion status, List<Employee> team, String createdDate, String lastUpdate, TaskTypes taskType, BugStatus bugStatus, String bugDescription) {
//        return null;
//    }
//
//    @Override
//    public Result deleteTask(Task task) {
//        return null;
//    }
//
//    @Override
//    public Result getTask(Employee employee, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskById(Employee employee, long taskId) {
//        return null;
//    }
//
//    @Override
//    public Result getTaskListById(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result deleteProject(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result updateProject(Project project) {
//        return null;
//    }
//
//    @Override
//    public Result createProject(String title, String takeIntoDevelopment, List<Task> tasks) {
//        return null;
//    }
//
//    @Override
//    public Result getProject(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result getProjectById(Employee employee, long projectId) {
//        return null;
//    }
//
//    @Override
//    public Result getProjecListById(Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result correctEmployeeParameters(Employee editedEmployee) {
//        return null;
//    }
//
//    @Override
//    public Result addEmployeeToTask(Task task, Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result deleteEmployeeFromTask(Task task, Employee employee) {
//        return null;
//    }
//
//    @Override
//    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department){
//        return null;
//    }
//
//    @Override
//    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfDevelopers status, ProgrammingLanguage language) {
//        return null;
//    }
//
//    @Override
//    public Result createEmployee(String firstName, String lastName, String login, String password, String email,String token, String department, TypeOfDevelopers status, ProgrammingLanguage language,TypeOfTester typeOfTester) {
//        return null;
//    }
//
////    public <T> List<T> select(Class cl) throws Exception {
////        //Подключаемся к считывающему потоку из файла
////        FileReader file = new FileReader(this.getPath(cl));
////        //Определяем сериалайзер
////        Serializer serializer = new Persister();
////        //Определяем контейнер и записываем в него объекты
////        WrapperXML xml = serializer.read(WrapperXML.class, file);
////        //Если список null, то делаем его пустым списком
////        if (xml.getList() == null) xml.setList(new ArrayList<T>());
////        //Возвращаем список объектов
////        return xml.getList();
////    }
//
//}
