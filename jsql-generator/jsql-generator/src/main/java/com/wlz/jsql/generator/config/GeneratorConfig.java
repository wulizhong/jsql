package com.wlz.jsql.generator.config;

public class GeneratorConfig {

    public static Builder createBuilder(){
        return new Builder();
    }

    private BaseDaoConfig baseDaoConfig;

    private DTOConfig dtoConfig;

    private EntityConfig entityConfig;

    private TableConfig tableConfig;

    private TConfig tConfig;

    private DaoConfig daoConfig;

    private BaseServiceConfig baseServiceConfig;
    private ServiceConfig serviceConfig;

    public GeneratorConfig(BaseDaoConfig baseDaoConfig,
                           DTOConfig dtoConfig,
                           EntityConfig entityConfig,
                           TableConfig tableConfig,
                           TConfig tConfig,
                           BaseServiceConfig baseServiceConfig,
                           ServiceConfig serviceConfig) {
        this.baseDaoConfig = baseDaoConfig;
        this.dtoConfig = dtoConfig;
        this.entityConfig = entityConfig;
        this.tableConfig = tableConfig;
        this.tConfig = tConfig;
        this.baseServiceConfig = baseServiceConfig;
        this.serviceConfig = serviceConfig;

    }

    public BaseDaoConfig getBaseDaoConfig() {
        return baseDaoConfig;
    }

    public void setBaseDaoConfig(BaseDaoConfig baseDaoConfig) {
        this.baseDaoConfig = baseDaoConfig;
    }

    public DTOConfig getDtoConfig() {
        return dtoConfig;
    }

    public void setDtoConfig(DTOConfig dtoConfig) {
        this.dtoConfig = dtoConfig;
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    public void setEntityConfig(EntityConfig entityConfig) {
        this.entityConfig = entityConfig;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
    }

    public TConfig getTConfig() {
        return tConfig;
    }

    public void setTConfig(TConfig tConfig) {
        this.tConfig = tConfig;
    }


    public DaoConfig getDaoConfig() {
        return daoConfig;
    }

    public void setDaoConfig(DaoConfig daoConfig) {
        this.daoConfig = daoConfig;
    }

    public BaseServiceConfig getBaseServiceConfig() {
        return baseServiceConfig;
    }

    public void setBaseServiceConfig(BaseServiceConfig baseServiceConfig) {
        this.baseServiceConfig = baseServiceConfig;
    }

    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public static class Builder{

        public GeneratorConfig build(){
            GeneratorConfig config = new GeneratorConfig(baseDaoConfig,dtoConfig,entityConfig,tableConfig,tConfig,baseServiceConfig,serviceConfig);
            config.setDaoConfig(daoConfig);
            return config;
        }


        private BaseDaoConfig baseDaoConfig;

        private DTOConfig dtoConfig;

        private EntityConfig entityConfig;

        private TableConfig tableConfig;

        private TConfig tConfig;
        private DaoConfig daoConfig;

        private BaseServiceConfig baseServiceConfig;
        private ServiceConfig serviceConfig;

        public Builder setBaseDaoConfig(BaseDaoConfig baseDaoConfig) {
            this.baseDaoConfig = baseDaoConfig;
            return this;
        }

        public Builder setDtoConfig(DTOConfig dtoConfig) {
            this.dtoConfig = dtoConfig;
            return this;
        }

        public Builder setEntityConfig(EntityConfig entityConfig) {
            this.entityConfig = entityConfig;
            return this;
        }

        public Builder setTableConfig(TableConfig tableConfig) {
            this.tableConfig = tableConfig;
            return this;
        }

        public Builder setDaoConfig(DaoConfig daoConfig) {
            this.daoConfig = daoConfig;
            return this;
        }

        public void setBaseServiceConfig(BaseServiceConfig baseServiceConfig) {
            this.baseServiceConfig = baseServiceConfig;
        }

        public void setServiceConfig(ServiceConfig serviceConfig) {
            this.serviceConfig = serviceConfig;
        }

        //        public Builder setTConfig(TConfig tConfig) {
//            this.tConfig = tConfig;
//            return this;
//        }
    }
}
