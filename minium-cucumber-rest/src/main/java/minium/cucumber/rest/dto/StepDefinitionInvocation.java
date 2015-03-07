/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.cucumber.rest.dto;

import gherkin.I18n;
import gherkin.formatter.model.DocString;

import java.io.Serializable;
import java.lang.reflect.Type;

import cucumber.api.DataTable;
import cucumber.runtime.ParameterInfo;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.table.TableConverter;
import cucumber.runtime.xstream.LocalizedXStreams.LocalizedXStream;

public class StepDefinitionInvocation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isoCode;
    private Object[] args;
    private DataTableDTO dataTable;
    private DocStringDTO docString;

    public StepDefinitionInvocation() {
    }

    public StepDefinitionInvocation(I18n isoCode, Object[] args) {
        this.isoCode = isoCode.getIsoCode();
        initializeArgs(args);
    }

    public Object[] getArgs(LocalizedXStream xStream, StepDefinition stepDefinition) {
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

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public DataTableDTO getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTableDTO dataTable) {
        this.dataTable = dataTable;
    }

    public DocStringDTO getDocString() {
        return docString;
    }

    public void setDocString(DocStringDTO docString) {
        this.docString = docString;
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
            dataTable = new DataTableDTO((DataTable) last);
            size = size - 1;
        } else if (last instanceof DocString) {
            docString = new DocStringDTO((DocString) last);
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