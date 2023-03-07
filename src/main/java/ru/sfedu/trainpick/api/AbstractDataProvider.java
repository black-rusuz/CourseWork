package ru.sfedu.trainpick.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.trainpick.model.HistoryContent;
import ru.sfedu.trainpick.utils.ConfigurationUtil;
import ru.sfedu.trainpick.utils.Constants;
import ru.sfedu.trainpick.utils.MongoUtil;
import ru.sfedu.trainpick.utils.ReflectUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractDataProvider {
    protected final Logger log = LogManager.getLogger(this.getClass());

    private boolean MONGO_ENABLE = false;
    private String MONGO_ACTOR = "";

    public AbstractDataProvider() {
        try {
            MONGO_ENABLE = Boolean.parseBoolean(ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ENABLE));
            MONGO_ACTOR = ConfigurationUtil.getConfigurationEntry(Constants.MONGO_ACTOR);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    // ABSTRACT GENERICS

    protected abstract <T> List<T> getAll(Class<T> type);

    protected abstract <T> T getById(Class<T> type, long id);

    protected abstract <T> long insert(Class<T> type, T bean);

    protected abstract <T> boolean delete(Class<T> type, long id);

    protected abstract <T> boolean update(Class<T> type, T bean);


    // SERVICE

    protected void sendLogs(String methodName, Object bean, boolean result) {
        HistoryContent historyContent = new HistoryContent(UUID.randomUUID(),
                this.getClass().getSimpleName(),
                LocalDateTime.now().toString(),
                MONGO_ACTOR,
                methodName,
                MongoUtil.objectToString(bean),
                result);
        if (MONGO_ENABLE) MongoUtil.saveToLog(historyContent);
    }

    protected <T> boolean hasSavedId(Class<T> type, long id) {
        T oldBean = getById(type, id);
        return ReflectUtil.getId(oldBean) != 0;
    }

    protected <T> String getNotFoundMessage(Class<T> type, long id) {
        return String.format(Constants.NOT_FOUND, type.getSimpleName(), id);
    }


    // USE CASES


    // CRUD
}
