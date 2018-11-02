package fuzhaohui.document;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class AppAbsutTest extends SpringJUnit4ClassRunner {
    static{
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {

            File directory = new File("");//设定为当前文件夹
            //获取当前毒经
            configurator.doConfigure(directory.getCanonicalPath().toString()+"/excel/src/main/resource/logback.xml");
        } catch (JoranException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public AppAbsutTest(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    private  static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        System.out.println(222222222222222L);
        logger.error("error");
        logger.info("info");
        logger.debug("333");
//　　　　　　//logger.error("logback 成功了");
//　　　　　//　logger.debug("logback 成功了");
    }

}
