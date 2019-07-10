package syr.design.chat.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

/**
 * 代码生成器
 *
 * @author syr
 * @since 2019/3/5
 */
public class CodeGenerator {

    /**
     * 读取控制台内容
     */
    private static String scanner() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + "表名" + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + "表名" + "！");
    }

    /**
     * 代码生成
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        String microProjectPath = "/";
        gc.setOutputDir(projectPath + microProjectPath + "/src/main/java");
        gc.setAuthor("syr");
        gc.setOpen(false);
        gc.setSwagger2(false);
        gc.setActiveRecord(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(false);
        gc.setIdType(IdType.ID_WORKER);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://140.143.123.76:3306/chat?useSSL=false&characterEncoding=utf8&allowMultiQueries=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("syr1996");
        dsc.setPassword("syr1996@Aa720325");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setEntity("model");
        pc.setParent("syr.design.chat");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("BaseController");
        strategy.setInclude(scanner());
        //strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix("p_", "t_", "w_");
        mpg.setStrategy(strategy);
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}