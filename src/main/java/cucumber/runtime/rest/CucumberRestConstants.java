package cucumber.runtime.rest;

public class CucumberRestConstants {

    public static final String URL_PREFIX        = "/cucumber";

    public static final String GLUES_URI          = "/backends/{backendId}/glues";
    public static final String GLUE_URI           = "/backends/{backendId}/glues/{uuid}";
    public static final String WORLDS_URI         = "/backends/{backendId}/worlds";
    public static final String WORLD_URI          = "/backends/{backendId}/worlds/{uuid}";
    public static final String HOOK_EXEC_URI      = "/backends/{backendId}/glues/{uuid}/hookDefinitions/{id}/execution";
    public static final String HOOK_TAG_MATCH_URI = "/backends/{backendId}/glues/{uuid}/hookDefinitions/{id}/matches";
    public static final String STEP_EXEC_URI      = "/backends/{backendId}/glues/{uuid}/stepDefinitions/{id}/execution";
    public static final String STEP_MATCHED_URI   = "/backends/{backendId}/glues/{uuid}/stepDefinitions/{id}/matchedArguments";
    public static final String SNIPPET_URI        = "/backends/{backendId}/glues/{uuid}/snippet";
}
