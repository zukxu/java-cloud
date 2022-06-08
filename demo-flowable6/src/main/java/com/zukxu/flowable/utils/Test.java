RepositoryService repositoryService = SpringUtil.getBean(RepositoryService.class);
        //String defId = "process_test:1:97230092-e70c-11ec-8a66-00ffaabbccdd";
        String defId = String.valueOf(param.get("defId"));
        BpmnModel bpmnModel = repositoryService.getBpmnModel(defId);
        //获得流程节点信息
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        List<UserTask> userTasks = new ArrayList<>();
        for (FlowElement flowElement : flowElements) {
        if (flowElement instanceof UserTask) {
        UserTask userTask = (UserTask)flowElement;
        System.out.println(userTask.getId() + ":" + userTask.getName());
        }
        }