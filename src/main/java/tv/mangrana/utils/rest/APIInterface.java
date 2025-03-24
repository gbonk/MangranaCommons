package tv.mangrana.utils.rest;

public interface APIInterface {
    enum ProtocolURLMark {
        HTTPS("http://");
        private final String mark;
        ProtocolURLMark(String mark) {
            this.mark = mark;
        }
        public String getMark() {
            return mark;
        }
    }
}
