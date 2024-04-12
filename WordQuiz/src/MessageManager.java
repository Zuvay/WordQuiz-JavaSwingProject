public enum MessageManager {


        STREAK_MESSAGE("Art arda doğru sayısı: "),
        SUCCESS_MESSAGE("Doğru Cevap !"),
        FAILURE_MESSAGE("Yanlış cevap! Doğrusu => "),
        LAST_STREAK_MESSAGE("Son seri sayısı: ");


        private final String value;

        MessageManager(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

