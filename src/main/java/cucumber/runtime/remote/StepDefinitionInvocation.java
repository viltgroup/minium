package cucumber.runtime.remote;

import gherkin.I18n;
import gherkin.formatter.model.DocString;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import cucumber.api.DataTable;
import cucumber.runtime.ParameterInfo;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.table.TableConverter;
import cucumber.runtime.xstream.LocalizedXStreams;
import cucumber.runtime.xstream.LocalizedXStreams.LocalizedXStream;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class StepDefinitionInvocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isoCode;
    private Object[] args;
    private DataTableProxy dataTable;
    private DocStringProxy docString;

    public StepDefinitionInvocation() {
    }

    public StepDefinitionInvocation(I18n isoCode, Object[] args) {
        this.isoCode = isoCode.getIsoCode();
        initializeArgs(args);
    }

    public Object[] getArgs(LocalizedXStreams xStreams, StepDefinition stepDefinition) {
        LocalizedXStream xStream = xStreams.get(getI18n().getLocale());
        Object[] transformedArgs = new Object[dataTable == null && docString == null ? args.length : args.length + 1];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg != null) {
                ParameterInfo parameterInfo = getParameterType(stepDefinition, i, arg.getClass());
                transformedArgs[i] = parameterInfo.convert(arg.toString(), xStream);
            }
        }
        if (dataTable != null) {
            ParameterInfo parameterInfo = getParameterType(stepDefinition, args.length, DataTable.class);
            TableConverter tableConverter = new TableConverter(xStream, parameterInfo);
            DataTable cucumberDataTable = dataTable.toDataTable(tableConverter);
            transformedArgs[args.length] =  tableConverter.convert(cucumberDataTable, parameterInfo.getType(), parameterInfo.isTransposed());
        } else if (docString != null) {
            transformedArgs[args.length] = docString.getValue();
        }
        return transformedArgs;
    }

    public I18n getI18n() {
        return new I18n(isoCode);
    }

    protected void initializeArgs(Object[] args) {
        if (args == null) return;
        if (args.length == 0) {
            this.args = new Object[0];
            return;
        }

        int size = args.length;
        Object last = args[size - 1];
        if (last instanceof DataTable) {
            dataTable = new DataTableProxy((DataTable) last);
            size = size - 1;
        } else if (last instanceof DocString) {
            docString = new DocStringProxy((DocString) last);
            size = size - 1;
        }

        this.args = new Object[size];
        System.arraycopy(args, 0, this.args, 0, size);
    }

    protected ParameterInfo getParameterType(StepDefinition stepDefinition, int n, Type argumentType) {
        ParameterInfo parameterInfo = stepDefinition.getParameterType(n, argumentType);
        if (parameterInfo == null) {
            // Some backends return null because they don't know
            parameterInfo = new ParameterInfo(argumentType, null, null, false, null);
        }
        return parameterInfo;
    }
}