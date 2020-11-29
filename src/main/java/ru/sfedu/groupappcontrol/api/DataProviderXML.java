package ru.sfedu.groupappcontrol.api;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.BaseClass;
import ru.sfedu.groupappcontrol.models.constants.Constants;
import ru.sfedu.groupappcontrol.models.constants.WrapperXML;
import ru.sfedu.groupappcontrol.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Complete;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Fail;

public class DataProviderXML implements DataProvider {
    private final String PATH = ConfigurationUtil.getConfigurationEntry("XML_PATH");

    private final String FILE_EXTENSION_XML ="FILE_EXTENSION_XML";

    private Logger log = LogManager.getLogger(DataProviderXML.class);

    public DataProviderXML() throws IOException {
    }

    public void createDirectories(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        Files.createDirectories(path);
    }

    public String getPath(Class cl) throws IOException {
        return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION_XML);
    }


    public void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            Path dirPath = Paths.get(PATH);
            Files.createDirectories(dirPath);
            file.createNewFile();
        }
    }

    public <T extends BaseClass> Result<T> getByID(Class cl, int id) throws Exception {
        Result list = this.select(cl);
        if (list.getStatus() == Fail) {
            return list;
        }
        List<T> listRes = (List<T>) list.getData();
        try {
            T element = listRes.stream().filter(el -> el.getId() == id).findFirst().get();
            return new Result(Complete, Constants.IS_INSERTED, element);
        } catch (NoSuchElementException e) {
            log.error("There is no element with this id");
            return new Result(Fail, Constants.IS_FAILED, null);
        }
    }



//    public Employee getEmployeeByID(int id) throws Exception {
//        List<Employee> employeeList = select(Employee.class);
//        try{
//            Employee employee = employeeList.stream()
//                    .filter(el->el.getId()==id)
//                    .limit(1)
//                    .findFirst().get();
//            return employee;
//        }catch (NoSuchElementException e){
//            log.error(e);
//            return new Employee();
//        }
//    }

    public <T extends BaseClass> Result<T> insert(Class cl, List<T> list, boolean append) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try {
            String path = getPath(cl);
            createFile(path);
            List<T> oldList = (List<T>) this.select(cl).getData();
            if (append) {
                if (oldList != null && oldList.size() > 0) {
                    int id = (int) list.get(0).getId();
                    if (oldList.stream().anyMatch(el -> el.getId() == id)) {
                        return new Result<>(Fail, Constants.IS_FAILED, null);
                    }
                    list = Stream
                            .concat(list.stream(), oldList.stream())
                            .collect(Collectors.toList());
                }
            }
            FileWriter writer = new FileWriter(path);
            Serializer serializer = new Persister();
            WrapperXML<T> xml = new WrapperXML<T>();
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            xml.setList(list);
            serializer.write(xml, writer);
            writer.close();
        } catch (IndexOutOfBoundsException ex) {
            return new Result<>(Fail, Constants.IS_EMPTY, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(Complete, Constants.IS_INSERTED, list);
    }

//    public void insertEmployee(List<Employee> listEmployee, boolean append) throws IOException {
//        try {
//            List<Employee> oldEmployeeList=this.getEmployees();
//            oldEmployeeList.addAll(listEmployee);
//            (new File(this.getPath(Employee.class))).createNewFile();
//            //Подключаемся к потоку записи файла
//            FileWriter writer = new FileWriter(this.getPath(Employee.class), false);
//            //Определяем сериалайзер
//            Serializer serializer = new Persister();
//
//            //Определяем контейнер, в котором будут находиться все объекты
//            WrapperXML<Employee> xml = new WrapperXML<Employee>();
//            //Записываем список объектов в котнейнер
//
//            if (append){
//                xml.setList(oldEmployeeList);
//                serializer.write(xml, writer);
//            }
//            else{
//                xml.setList(listEmployee);
//                serializer.write(xml, writer);
//            }
//            writer.close();
//        }catch (IndexOutOfBoundsException e){
//            log.error("List is empty.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public <T extends BaseClass> Result<T> delete(Class cl, int id) throws Exception {
        Result list = select(cl);
        List<T> listData = (List<T>) list.getData();
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insert(cl, listData, false);
        return new Result(Complete, Constants.IS_INSERTED, listData);
    }

//    public void deleteEmployee(int id) throws Exception {
//        List<Employee> employeeList=getEmployees();
//        employeeList = employeeList.stream().filter(el->el.getId()!=id).collect(Collectors.toList());
//        insertEmployee(employeeList,false);
//    }

    public <T extends BaseClass> Result<T> update(Class cl, int id) throws Exception {
        delete(cl, id);
        List<T> list = new ArrayList<T>();
        list.addAll(list);
        insert(cl, list, true);
        return new Result(Complete, Constants.IS_INSERTED, list);
    }

//    public void updateEmployee(Employee employee) throws Exception {
//        deleteEmployee((int) employee.getId());
//        List<Employee> employeeList= new ArrayList<Employee>();
//        employeeList.add(employee);
//        insertEmployee(employeeList,true);
//    }

    public <T> Result<T> select(Class cl) throws Exception {
        String path = getPath(cl);
        FileReader file = new FileReader(path);
        Serializer serializer = new Persister();
        WrapperXML xml = serializer.read(WrapperXML.class, file);
        return new Result(Complete, Constants.IS_INSERTED, xml.list);
    }

//    public <T> List<T> select(Class cl) throws Exception {
//        //Подключаемся к считывающему потоку из файла
//        FileReader file = new FileReader(this.getPath(cl));
//        //Определяем сериалайзер
//        Serializer serializer = new Persister();
//        //Определяем контейнер и записываем в него объекты
//        WrapperXML xml = serializer.read(WrapperXML.class, file);
//        //Если список null, то делаем его пустым списком
//        if (xml.getList() == null) xml.setList(new ArrayList<T>());
//        //Возвращаем список объектов
//        return xml.getList();
//    }

}
