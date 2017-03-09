## base_framework

spring-boot版本base framework

1.spring boot

2.spring data jpa, spring data redis

3.多数据源配置

4.swagger

5.全局异常处理


### JpaHelper的使用方法 
    JpaHelper<Team> helper = new JpaHelper<>();
          Specification<Team> spec = Specifications.where(helper.eq("status", search.getStatus())).
                          // 对应相应的查询条件
                          and(helper.eq("teamType", search.getTeamType())).
                          and(helper.gte("insDate", DateUtils.getStart(search.getStartTime()))).
                          and(helper.lte("insDate", DateUtils.getEnd(search.getEndTime()))).
                          and(helper.like("teamName", search.getTeamName()));