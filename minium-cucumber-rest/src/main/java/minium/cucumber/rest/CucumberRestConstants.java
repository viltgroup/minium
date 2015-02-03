package minium.cucumber.rest;

public class CucumberRestConstants {

    public static final String URL_PREFIX        = "/cucumber";

    public static final String BACKEND_PREFIX    = "/backends/{backendId}";

    public static final String GLUES_URI          = "/glues";
    public static final String GLUE_URI           = "/glues/{uuid}";
    public static final String WORLDS_URI         = "/worlds";
    public static final String WORLD_URI          = "/worlds/{uuid}";
    public static final String HOOK_EXEC_URI      = "/glues/{uuid}/hookDefinitions/{id}/execution";
    public static final String HOOK_TAG_MATCH_URI = "/glues/{uuid}/hookDefinitions/{id}/matches";
    public static final String STEP_EXEC_URI      = "/glues/{uuid}/stepDefinitions/{id}/execution";
    public static final String STEP_MATCHED_URI   = "/glues/{uuid}/stepDefinitions/{id}/matchedArguments";
    public static final String SNIPPET_URI        = "/snippet";
}
