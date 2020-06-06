package org.elasticsearch.index.analysis;


import com.huaban.analysis.jieba.WordDictionary;
import org.elasticsearch.env.Environment;

import java.util.Timer;
import java.util.TimerTask;

public class JiebaDict {

    private static JiebaDict singleton;

    public static JiebaDict init(Environment environment) {
        if (singleton == null) {
            synchronized (JiebaDict.class) {
                if (singleton == null) {

                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("start to load new dict");
                            WordDictionary.getInstance()
                                    .init(environment.pluginsFile().resolve("jieba/dic").toFile());
                        }
                    };

                    Timer t=new Timer();

                    t.scheduleAtFixedRate(task,1000, 60 * 1000);

                    singleton = new JiebaDict();
                    return singleton;
                }
            }
        }
        return singleton;
    }
}
