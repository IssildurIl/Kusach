package ru.sfedu.groupappcontrol.api;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.groupappcontrol.models.Employee;
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
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class DataProviderXML implements IDataProvider{
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

    public List<Employee> getEmployees() throws Exception {
        return select(Employee.class);
    }

    public Employee getEmployeeByID(int id) throws Exception {
        List<Employee> employeeList = select(Employee.class);
        try{
            Employee employee = employeeList.stream()
                    .filter(el->el.getId()==id)
                    .limit(1)
                    .findFirst().get();
            return employee;
        }catch (NoSuchElementException e){
            log.error(e);
            return new Employee();
        }
    }

    public void insertEmployee(List<Employee> listEmployee, boolean append) throws IOException {
        try {
            List<Employee> oldEmployeeList=this.getEmployees();
            oldEmployeeList.addAll(listEmployee);
            (new File(this.getPath(Employee.class))).createNewFile();
            //Подключаемся к потоку записи файла
            FileWriter writer = new FileWriter(this.getPath(Employee.class), false);
            //Определяем сериалайзер
            Serializer serializer = new Persister();

            //Определяем контейнер, в котором будут находиться все объекты
            WrapperXML<Employee> xml = new WrapperXML<Employee>();
            //Записываем список объектов в котнейнер

            if (append){
                xml.setList(oldEmployeeList);
                serializer.write(xml, writer);
            }
            else{
                xml.setList(listEmployee);
                serializer.write(xml, writer);
            }
            writer.close();
        }catch (IndexOutOfBoundsException e){
            log.error("List is empty.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void deleteEmployee(int id) throws Exception {
        List<Employee> employeeList=getEmployees();
        employeeList = employeeList.stream().filter(el->el.getId()!=id).collect(Collectors.toList());
        insertEmployee(employeeList,false);
    }

    public void updateEmployee(Employee employee) throws Exception {
        deleteEmployee((int) employee.getId());
        List<Employee> employeeList= new ArrayList<Employee>();
        employeeList.add(employee);
        insertEmployee(employeeList,true);
    }

    public <T> List<T> select(Class cl) throws Exception {
        //Подключаемся к считывающему потоку из файла
        FileReader file = new FileReader(this.getPath(cl));
        //Определяем сериалайзер
        Serializer serializer = new Persister();
        //Определяем контейнер и записываем в него объекты
        WrapperXML xml = serializer.read(WrapperXML.class, file);
        //Если список null, то делаем его пустым списком
        if (xml.getList() == null) xml.setList(new ArrayList<T>());
        //Возвращаем список объектов
        return xml.getList();
    }

}
