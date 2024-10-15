package com.wlz.jsql.generator;

import com.wlz.jsql.generator.config.BaseServiceConfig;
import com.wlz.jsql.generator.config.GeneratorConfig;
import com.wlz.jsql.generator.config.ServiceConfig;
import com.wlz.jsql.generator.generator.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

public class JSqlGenerator {


    private String url;
    private String account;
    private String password;
    private String basePath;

    private String tpackage;

    private List<GeneratorConfig> generatorConfigList = new ArrayList<>();


    public void generate(){
        try{
            MySqlTableDriver mySqlTableDriver = new MySqlTableDriver();

            Connection connection = DriverManager.getConnection(url, account, password);
            List<String> tables = mySqlTableDriver.getAllTableName(connection);

            Set<String> prefixList = new HashSet<>();
            Set<String> tableList = new HashSet<>();
            Set<String> tablePackageList = new HashSet<>();
            for(GeneratorConfig generatorConfig : generatorConfigList){

                for(String tableName:tables) {

                    if(tableName.equals(generatorConfig.getTableConfig().getTableName())) {
                        if(generatorConfig.getEntityConfig()!=null){
                            Table table = mySqlTableDriver.getTable(connection, tableName);
                            tableList.add(tableName);
                            EntityGenerator eg = new EntityGenerator(generatorConfig.getEntityConfig().getPackageName(),table);
                            prefixList.add(generatorConfig.getTableConfig().getPrefix());
                            eg.setPrefix(generatorConfig.getTableConfig().getPrefix());
                            String code = eg.generateCode();

                            write(basePath,generatorConfig.getEntityConfig().getPackageName(),code,eg.getFileName());
                        }


                        if(generatorConfig.getDtoConfig()!=null) {
                            Table table = mySqlTableDriver.getTable(connection, tableName);
                            DTOGenerator generator = new DTOGenerator(generatorConfig.getDtoConfig().getPackageName(),generatorConfig.getEntityConfig().getPackageName(),table);
                            generator.setPrefix(generatorConfig.getTableConfig().getPrefix());
                            String code = generator.generateCode();
                            write(basePath,generatorConfig.getDtoConfig().getPackageName(),code,generator.getFileName());
                        }
                        if(generatorConfig.getTableConfig()!=null) {
                            Table table = mySqlTableDriver.getTable(connection, tableName);
                            TableGenerator generator = new TableGenerator(generatorConfig.getTableConfig().getPackageName(),generatorConfig.getEntityConfig().getPackageName(),table);
                            generator.setPrefix(generatorConfig.getTableConfig().getPrefix());
                            String code = generator.generateCode();
                            tablePackageList.add(generator.getClassPackage());
                            write(basePath,generatorConfig.getTableConfig().getPackageName(),code,generator.getFileName());

                        }

                        if(generatorConfig.getBaseDaoConfig()!=null) {
                            Table table = mySqlTableDriver.getTable(connection, tableName);
                            BaseDaoGenerator generator = new BaseDaoGenerator(generatorConfig.getBaseDaoConfig().getPackageName(),tpackage,generatorConfig.getEntityConfig().getPackageName(),table);
                            generator.setPrefix(generatorConfig.getTableConfig().getPrefix());
                            String code = generator.generateCode();
                            write(basePath,generatorConfig.getBaseDaoConfig().getPackageName(),code,generator.getFileName());
                        }

                        if(generatorConfig.getDaoConfig()!=null){
                            Table table = mySqlTableDriver.getTable(connection, tableName);
                            DaoGenerator generator = new DaoGenerator(generatorConfig.getDaoConfig().getPackageName(),generatorConfig.getBaseDaoConfig().getPackageName(),tpackage,table);
                            generator.setPrefix(generatorConfig.getTableConfig().getPrefix());
                            String code = generator.generateCode();
                            write(basePath,generatorConfig.getDaoConfig().getPackageName(),code,generator.getFileName());
                        }

                        if(generatorConfig.getBaseServiceConfig()!=null){
                            BaseServiceConfig config = generatorConfig.getBaseServiceConfig();
                            Table table = mySqlTableDriver.getTable(connection, tableName);
                            BaseServiceGenerator generator = new BaseServiceGenerator(config.getPackageName(),generatorConfig.getBaseDaoConfig().getPackageName(),generatorConfig.getEntityConfig().getPackageName(),table);
                            String code = generator.generateCode();
                            write(basePath,config.getPackageName(),code,generator.getFileName());
                        }

                        if(generatorConfig.getServiceConfig()!=null){
                            ServiceConfig config = generatorConfig.getServiceConfig();
                            Table table = mySqlTableDriver.getTable(connection, tableName);
                            ServiceGenerator generator = new ServiceGenerator(config.getPackageName(),
                                    generatorConfig.getBaseServiceConfig().getPackageName(),table,tpackage,generatorConfig.getDaoConfig().getPackageName());
                            generator.setPrefix(generatorConfig.getTableConfig().getPrefix());
                            String code = generator.generateCode();
                            write(basePath,config.getPackageName(),code,generator.getFileName());
                        }

                    }

                }

            }
            List<String> tempTableList = new ArrayList<>();
            tempTableList.addAll(tableList);
            List<String> tempPrefixList = new ArrayList<>();
            tempPrefixList.addAll(prefixList);
            TablesGenerator generator = new TablesGenerator(tpackage,tempTableList);
            generator.setPrefixList(tempPrefixList);
            List<String> tempClassPackageList = new ArrayList<>();
            tempClassPackageList.addAll(tablePackageList);
            generator.setClassPackageList(tempClassPackageList);
            String code = generator.generateCode();
            write(basePath,tpackage,code,generator.getFileName());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void add(GeneratorConfig generatorConfig){
        generatorConfigList.add(generatorConfig);
    }

    public JSqlGenerator(String url, String account, String password, String basePath) {
        this.url = url;
        this.account = account;
        this.password = password;
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    void write(String basePath,String packageName,String code,String fileName) throws FileNotFoundException, IOException {

        packageName = packageName.replace(".", "\\");
        String path = basePath+packageName;
        File folder = new File(path);
        if(!folder.exists()) {
            folder.mkdirs();
        }
        IOUtils.write(code, new FileOutputStream(new File(path+File.separator+fileName)));

    }

    public String getTPackage() {
        return tpackage;
    }

    public void setTPackage(String TPackage) {
        this.tpackage = TPackage;
    }
}
