package ru.sfedu.groupappcontrol.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.groupappcontrol.Result;
import ru.sfedu.groupappcontrol.models.BaseClass;
import ru.sfedu.groupappcontrol.models.constants.Constants;
import ru.sfedu.groupappcontrol.models.enums.Outcomes;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Complete;
import static ru.sfedu.groupappcontrol.models.enums.Outcomes.Fail;

public class DataProviderCsv implements IDataProvider {

    private final String PATH = ConfigurationUtil.getConfigurationEntry("CSV_PATH");

    private final String FILE_EXTENSION_CSV = "FILE_EXTENSION_CSV";
    private Logger log = LogManager.getLogger(DataProviderCsv.class);

    public DataProviderCsv() throws IOException {
    }

    public void createDirectories(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        Files.createDirectories(path);
    }

    public String getPath(Class cl) throws IOException {
        return PATH + cl.getSimpleName().toLowerCase() + ConfigurationUtil.getConfigurationEntry(FILE_EXTENSION_CSV);
    }

    public void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            Path dirPath = Paths.get(PATH);
            Files.createDirectories(dirPath);
            file.createNewFile();
        }
    }


    public <T extends BaseClass> Result<T> getByID(Class cl, int id) throws IOException {
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

    public <T extends BaseClass> Result<T> delete(Class cl, int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Result list = select(cl);
        List<T> listData = (List<T>) list.getData();
        listData = listData.stream().filter(el -> el.getId() != id).collect(Collectors.toList());
        insert(cl, listData, false);
        return new Result(Complete, Constants.IS_INSERTED, listData);
    }

    public <T extends BaseClass> Result<T> update(Class cl, int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        delete(cl, id);
        List<T> list = new ArrayList<T>();
        list.addAll(list);
        insert(cl, list, true);
        return new Result(Complete, Constants.IS_INSERTED, list);
    }

    public <T> Result<T> select(Class cl) throws IOException {
        String path = getPath(cl);
        FileReader file = new FileReader(path);
        CSVReader reader = new CSVReader(file);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withType(cl)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        List<T> list = csvToBean.parse();
        reader.close();
        return new Result(Complete, Constants.IS_INSERTED, list);
    }


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
            FileWriter file = new FileWriter(path);
            CSVWriter writer = new CSVWriter(file);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(list);
            writer.close();
        } catch (IndexOutOfBoundsException ex) {
            return new Result<>(Fail, Constants.IS_EMPTY, null);
        }
        return new Result(Complete, Constants.IS_INSERTED, list);
    }
}