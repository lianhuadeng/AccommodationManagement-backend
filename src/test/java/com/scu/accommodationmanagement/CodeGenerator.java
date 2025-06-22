package com.scu.accommodationmanagement;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

public class CodeGenerator {

    /**
     * Project package
     */
    private static String projectPackage;

    /**
     * controller package
     */
    private static String controllerPackage;

    /**
     * entity package
     */
    private static String entityPackage;

    /**
     * author
     */
    private static String author;

    /**
     * Database url
     */
    private static String url;
    /**
     * Database username
     */
    private static String username;
    /**
     * Database password
     */
    private static String password;
    /**
     * Database driver class
     */
    private static String driverClass;

    /**
     * 文件名后缀
     */
    private static String fileSuffix = ".java";

    /**
     * Init database information
     */
    static {
        Properties properties = new Properties();
        InputStream i = CodeGenerator.class.getResourceAsStream("/mybatis-plus.properties");
        try {
            properties.load(i);
            projectPackage = properties.getProperty("generator.parent.package");
            controllerPackage = properties.getProperty("controller.package");
            entityPackage = properties.getProperty("entity.package");
            url = properties.getProperty("generator.jdbc.url");
            username = properties.getProperty("generator.jdbc.username");
            password = properties.getProperty("generator.jdbc.password");
            driverClass = properties.getProperty("generator.jdbc.driverClass");
            author = properties.getProperty("author");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        System.out.println("开始生成。。。");
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()//不打开生成目录
                            .commentDate("yyyy-MM-dd") // 设置注释日期
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent(projectPackage) // 设置父包名
                                .entity(entityPackage)  // 设置实体包名
                                .service("service")  // 设置服务接口包名
                                .serviceImpl("service.impl")  // 设置服务实现包名
                                .mapper("mapper")  // 设置mapper包名
                                .xml("mapper.xml")  // 设置xml包名
                                .controller(controllerPackage)  // 设置controller包名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper/")) // 设置mapperXml生成路径
                )
                .injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, stringObjectMap) -> {
                                System.out.println("生成文件："+tableInfo.getEntityName());
                                System.out.println("生成文件："+tableInfo.getMapperName());
                                System.out.println("生成文件："+tableInfo.getXmlName());
                                System.out.println("生成文件："+tableInfo.getServiceName());
                                System.out.println("生成文件："+tableInfo.getServiceImplName());
                                System.out.println("生成文件："+tableInfo.getControllerName());
                            }) // 输出文件之前执行的逻辑，在生成文件之前执行自定义逻辑，如打印表信息或修改配置数据
                            .customMap(new HashMap<>()) // 自定义配置 Map 对象，用于在模板中访问自定义的配置信息，如项目名称、作者等
                            .customFile(new HashMap<>()); // 自定义配置模板文件，用于指定自定义的模板文件路径，可以格式化文件名
                })
                .strategyConfig((scanner, builder) ->
                        builder.addInclude("opration_log") // 设置需要生成的表名
//                                .addTablePrefix("t_") // 设置过滤表前缀
                                .entityBuilder().enableFileOverride()// entity存在及覆盖
//                                .enableLombok() // 启用 Lombok
                                .enableChainModel() // 开启链式模型（默认 false）
                                .enableRemoveIsPrefix() // 开启Boolean类型字段移除is前缀（默认 false）
//                                .logicDeleteColumnName("is_deleted") // 设置逻辑删除字段名（数据库字段）
//                                .logicDeletePropertyName("deleted") // 设置逻辑删除属性名（实体）
                                .enableTableFieldAnnotation() // 启用字段注解
                                .serviceBuilder().enableFileOverride()
                                .mapperBuilder().enableFileOverride()
                                .enableBaseResultMap()
                                .controllerBuilder()
                                .enableFileOverride()
                                .enableRestStyle() // 启用 REST 风格
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
        System.out.println("结束生成。。。");
    }
}
