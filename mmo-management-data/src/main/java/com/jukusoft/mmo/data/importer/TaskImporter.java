package com.jukusoft.mmo.data.importer;

import com.jukusoft.mmo.core.utils.ImportUtils;
import com.jukusoft.mmo.core.utils.ResourceUtils;
import com.jukusoft.mmo.data.dao.TaskDAO;
import com.jukusoft.mmo.data.schedular.TaskEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Configuration
@Profile("default")
public class TaskImporter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(TaskImporter.class);

    @Autowired
    private TaskDAO taskDAO;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!ImportUtils.isInitialImportEnabled()) {
            return;
        }

        logger.info("import tasks");

        Map<String, Long> lastExecutionMap = StreamSupport.stream(taskDAO.findAll().spliterator(), false)
                .collect(Collectors.toMap(task -> task.getClassName(), task -> task.getLastExecution()));
        List<String> oldTasks = StreamSupport.stream(taskDAO.findAll().spliterator(), false)
                .map(task -> task.getClassName())
                .collect(Collectors.toList());

        List<String> newTasks = new ArrayList<>();

        String jsonString = ResourceUtils.getResourceFileAsString("tasks/tasks.json");
        Objects.requireNonNull(jsonString);

        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);

            TaskEntity task = new TaskEntity();
            task.setClassName(json.getString("className"));
            task.setTitle(json.getString("title"));
            task.setDescription(json.getString("description"));
            task.setIntervalInSeconds(json.getLong("interval") * 60);

            //set old last execution timestamp, if task already exists
            if (lastExecutionMap.containsKey(task.getClassName())) {
                task.setLastExecution(lastExecutionMap.get(task.getClassName()));
            }

            logger.debug("import task: " + task.getClassName());
            taskDAO.save(task);

            newTasks.add(task.getClassName());
        }

        //remove old tasks, which are not in config anymore
        oldTasks.stream()
                .filter(className -> !newTasks.contains(className))
                .forEach(className -> {
                    logger.info("remove old task: " + className);
                    taskDAO.deleteById(className);
                });
    }

}
