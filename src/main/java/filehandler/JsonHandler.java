package filehandler;

import constant.FilePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

/**
 * Generic class to read and write bills,food,drink to file
 */
public class JsonHandler {
    private static final Logger LOGGER = LogManager.getLogger(JsonHandler.class);

    /**
     * @param <T> which class you want to get list. Example: getList(Food.class)
     * @return a list of T
     * @throws IOException
     */
    public static <T> List<T> getListFromFile(Class clazz) {
        String name = clazz.getSimpleName();
        String path = FilePath.FILE_PATH + name.toLowerCase(Locale.ROOT).concat(".json");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory t = TypeFactory.defaultInstance();
        try {
            List<T> list = objectMapper.readValue(Paths.get(path).toFile(), t.constructCollectionType(List.class, clazz));
            LOGGER.debug("[{}]", "GET " + clazz.getSimpleName() + " SUCCESSFULLY");
            return list;
        } catch (IOException e) {
            LOGGER.error("[{}]", "GET " + clazz.getSimpleName() + " UNSUCCESSFULLY");
            System.out.println("GET " + clazz.getSimpleName() + " UNSUCCESSFULLY");
            return null;
        }
    }

    /**
     * @param list: list you want to save to file
     * @param <T>:  type of element in list
     * @throws IOException
     */
    public static <T> void saveListToFile(List<T> list) throws IOException {
        Class clazz = list.get(0).getClass();
        String name = clazz.getSimpleName();
        String path = FilePath.FILE_PATH + name.toLowerCase(Locale.ROOT).concat(".json");
        ObjectMapper objectMapper = new ObjectMapper();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fileWriter, list);
            LOGGER.debug("[{}]", "SAVE " + clazz.getSimpleName() + " SUCCESSFULLY");
            fileWriter.close();
        } catch (IOException e) {
            LOGGER.error("[{}]", "SAVE " + clazz.getSimpleName() + " UNSUCCESSFULLY");
            System.out.println("SAVE UNSUCCESSFULLY");
            fileWriter.close();
        }
    }

}
