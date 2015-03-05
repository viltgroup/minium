package minium.web.actions;

import java.util.Date;

public interface Cookie {

    String getName();

    String getValue();

    String getDomain();

    String getPath();

    boolean isSecure();

    boolean isHttpOnly();

    Date getExpiry();

    public static class Builder
    {
        private final String name;
        private final String value;
        private String path;
        private String domain;
        private Date expiry;
        private boolean secure;
        private boolean httpOnly;

        public Builder(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public Cookie.Builder domain(String host) {
            this.domain = host;
            return this;
        }

        public Cookie.Builder path(String path) {
            this.path = path;
            return this;
        }

        public Cookie.Builder expiresOn(Date expiry) {
            this.expiry = expiry;
            return this;
        }

        public Cookie.Builder isSecure(boolean secure) {
            this.secure = secure;
            return this;
        }

        public Cookie.Builder isHttpOnly(boolean httpOnly) {
            this.httpOnly = httpOnly;
            return this;
        }

        public Cookie build() {
            return new Cookie() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public String getValue() {
                    return value;
                }

                @Override
                public String getDomain() {
                    return domain;
                }

                @Override
                public String getPath() {
                    return path;
                }

                @Override
                public boolean isSecure() {
                    return secure;
                }

                @Override
                public boolean isHttpOnly() {
                    return httpOnly;
                }

                @Override
                public Date getExpiry() {
                    return expiry;
                }
            };
        }
    }
}