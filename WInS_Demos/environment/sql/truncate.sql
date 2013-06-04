-- truncates tables otherwise WLS can get confused...
truncate table weblogic_cluster_domain.active drop storage;
-- truncate table saf_target_domain.active drop storage;
truncate table weblogic_cluster_domain.jmsstore1wlstore drop storage;
truncate table weblogic_cluster_domain.jmsstore2wlstore drop storage;
truncate table weblogic_cluster_domain.safstore1wlstore drop storage;
truncate table weblogic_cluster_domain.safstore2wlstore drop storage;

exit;
