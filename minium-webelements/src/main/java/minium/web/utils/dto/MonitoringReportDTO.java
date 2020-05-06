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
package minium.web.utils.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

public class MonitoringReportDTO {
    private String url;
    private Performance data;
    private Stats stats;
    private Integer statusCode;
    private List<LogEntry> jsErrors;

    public MonitoringReportDTO(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Performance getData() {
        return data;
    }

    public void setData(Performance data) {
        this.data = data;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<LogEntry> getJsErrors() {
        return jsErrors;
    }

    public void setJsErrors(List<LogEntry> jsErrors) {
        this.jsErrors = jsErrors;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Performance {
        private Timing timing;
        private Navigation navigation;
        private Long timeOrigin;

        public Timing getTiming() {
            return timing;
        }

        public void setTiming(Timing timing) {
            this.timing = timing;
        }

        public Navigation getNavigation() {
            return navigation;
        }

        public void setNavigation(Navigation navigation) {
            this.navigation = navigation;
        }

        public Long getTimeOrigin() {
            return timeOrigin;
        }

        public void setTimeOrigin(Long timeOrigin) {
            this.timeOrigin = timeOrigin;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        class Timing {
            private Long navigationStart;
            private Long unloadEventStart;
            private Long unloadEventEnd;
            private Long redirectStart;
            private Long redirectEnd;
            private Long fetchStart;
            private Long domainLookupStart;
            private Long domainLookupEnd;
            private Long connectStart;
            private Long connectEnd;
            private Long secureConnectionStart;
            private Long requestStart;
            private Long responseStart;
            private Long responseEnd;
            private Long domLoading;
            private Long domInteractive;
            private Long domContentLoadedEventStart;
            private Long domContentLoadedEventEnd;
            private Long domComplete;
            private Long loadEventStart;
            private Long loadEventEnd;

            public Timing() {
                // Do nothing
            }

            public Long getNavigationStart() {
                return navigationStart;
            }

            public void setNavigationStart(Long navigationStart) {
                this.navigationStart = navigationStart;
            }

            public Long getUnloadEventStart() {
                return unloadEventStart;
            }

            public void setUnloadEventStart(Long unloadEventStart) {
                this.unloadEventStart = unloadEventStart;
            }

            public Long getUnloadEventEnd() {
                return unloadEventEnd;
            }

            public void setUnloadEventEnd(Long unloadEventEnd) {
                this.unloadEventEnd = unloadEventEnd;
            }

            public Long getRedirectStart() {
                return redirectStart;
            }

            public void setRedirectStart(Long redirectStart) {
                this.redirectStart = redirectStart;
            }

            public Long getRedirectEnd() {
                return redirectEnd;
            }

            public void setRedirectEnd(Long redirectEnd) {
                this.redirectEnd = redirectEnd;
            }

            public Long getFetchStart() {
                return fetchStart;
            }

            public void setFetchStart(Long fetchStart) {
                this.fetchStart = fetchStart;
            }

            public Long getDomainLookupStart() {
                return domainLookupStart;
            }

            public void setDomainLookupStart(Long domainLookupStart) {
                this.domainLookupStart = domainLookupStart;
            }

            public Long getDomainLookupEnd() {
                return domainLookupEnd;
            }

            public void setDomainLookupEnd(Long domainLookupEnd) {
                this.domainLookupEnd = domainLookupEnd;
            }

            public Long getConnectStart() {
                return connectStart;
            }

            public void setConnectStart(Long connectStart) {
                this.connectStart = connectStart;
            }

            public Long getConnectEnd() {
                return connectEnd;
            }

            public void setConnectEnd(Long connectEnd) {
                this.connectEnd = connectEnd;
            }

            public Long getSecureConnectionStart() {
                return secureConnectionStart;
            }

            public void setSecureConnectionStart(Long secureConnectionStart) {
                this.secureConnectionStart = secureConnectionStart;
            }

            public Long getRequestStart() {
                return requestStart;
            }

            public void setRequestStart(Long requestStart) {
                this.requestStart = requestStart;
            }

            public Long getResponseStart() {
                return responseStart;
            }

            public void setResponseStart(Long responseStart) {
                this.responseStart = responseStart;
            }

            public Long getResponseEnd() {
                return responseEnd;
            }

            public void setResponseEnd(Long responseEnd) {
                this.responseEnd = responseEnd;
            }

            public Long getDomLoading() {
                return domLoading;
            }

            public void setDomLoading(Long domLoading) {
                this.domLoading = domLoading;
            }

            public Long getDomInteractive() {
                return domInteractive;
            }

            public void setDomInteractive(Long domInteractive) {
                this.domInteractive = domInteractive;
            }

            public Long getDomContentLoadedEventStart() {
                return domContentLoadedEventStart;
            }

            public void setDomContentLoadedEventStart(Long domContentLoadedEventStart) {
                this.domContentLoadedEventStart = domContentLoadedEventStart;
            }

            public Long getDomContentLoadedEventEnd() {
                return domContentLoadedEventEnd;
            }

            public void setDomContentLoadedEventEnd(Long domContentLoadedEventEnd) {
                this.domContentLoadedEventEnd = domContentLoadedEventEnd;
            }

            public Long getDomComplete() {
                return domComplete;
            }

            public void setDomComplete(Long domComplete) {
                this.domComplete = domComplete;
            }

            public Long getLoadEventStart() {
                return loadEventStart;
            }

            public void setLoadEventStart(Long loadEventStart) {
                this.loadEventStart = loadEventStart;
            }

            public Long getLoadEventEnd() {
                return loadEventEnd;
            }

            public void setLoadEventEnd(Long loadEventEnd) {
                this.loadEventEnd = loadEventEnd;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        class Navigation {
            private Short type;
            private Short redirectCount;

            public Navigation() {
                // Do nothing
            }

            public Short getType() {
                return type;
            }

            public void setType(Short type) {
                this.type = type;
            }

            public Short getRedirectCount() {
                return redirectCount;
            }

            public void setRedirectCount(Short redirectCount) {
                this.redirectCount = redirectCount;
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Stats {
        private Integer numberOfRequests;
        private Long pageSize;

        public Stats() {
            // Do nothing
        }

        public Integer getNumberOfRequests() {
            return numberOfRequests;
        }

        public void setNumberOfRequests(Integer numberOfRequests) {
            this.numberOfRequests = numberOfRequests;
        }

        public Long getPageSize() {
            return pageSize;
        }

        public void setPageSize(Long pageSize) {
            this.pageSize = pageSize;
        }
    }
}
