package com.epam.rd.contactbook;

public class Contact {
    private String name;
    private ContactInfo phone;
    private Email[] emails = new Email[3];
    private int emailCount = 0;
    private Social[] socials = new Social[5];
    private int socialCount = 0;

    public Contact(String contactName) {
        this.name = contactName;
    }

    public void rename(String newName) {
        if (newName != null && !newName.isEmpty()) {
            this.name = newName;
        }
    }

    public Email addEmail(String localPart, String domain) {
        if (emailCount >= 3) return null;

        Email email = new Email(localPart + "@" + domain);
        emails[emailCount++] = email;
        return email;
    }

    public Email addEpamEmail(String firstname, String lastname) {
        if (emailCount >= 3) return null;

        Email email = new Email(firstname + "_" + lastname + "@epam.com") {
            @Override
            public String getTitle() {
                return "Epam Email";
            }
        };

        emails[emailCount++] = email;
        return email;
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (phone != null) return null;

        phone = new ContactInfo() {
            @Override
            public String getTitle() {
                return "Tel";
            }

            @Override
            public String getValue() {
                return "+" + code + " " + number;
            }
        };

        return phone;
    }

    public Social addTwitter(String twitterId) {
        return addSocialMedia("Twitter", twitterId);
    }

    public Social addInstagram(String instagramId) {
        return addSocialMedia("Instagram", instagramId);
    }

    public Social addSocialMedia(String title, String id) {
        if (socialCount >= 5) return null;

        Social social = new Social(title, id);
        socials[socialCount++] = social;
        return social;
    }

    public ContactInfo[] getInfo() {

        int size = 1;

        if (phone != null) size++;

        size += emailCount;
        size += socialCount;

        ContactInfo[] result = new ContactInfo[size];

        int index = 0;

        result[index++] = new NameContactInfo();

        if (phone != null) {
            result[index++] = phone;
        }

        for (int i = 0; i < emailCount; i++) {
            result[index++] = emails[i];
        }

        for (int i = 0; i < socialCount; i++) {
            result[index++] = socials[i];
        }

        return result;
    }

    private class NameContactInfo implements ContactInfo {

        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return name;
        }
    }

    public static class Email implements ContactInfo {

        private final String value;

        public Email(String value) {
            this.value = value;
        }

        @Override
        public String getTitle() {
            return "Email";
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static class Social implements ContactInfo {

        private final String title;
        private final String value;

        public Social(String title, String value) {
            this.title = title;
            this.value = value;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}