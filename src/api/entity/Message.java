package api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {
    private long message_id;
    private User from;
    private int date;
    private Chat chat;
    private User forward_from;
    private Chat forward_from_chat;
    private int forward_date;
    private Message reply_to_message;
    private int edit_date;
    private String text;
    private List<MessageEntity> entities = new ArrayList<MessageEntity>();
    private Audio audio;
    private Document document;
    private List<PhotoSize> photo = new ArrayList<PhotoSize>();
    private Sticker sticker;
    private Video video;
    private Voice voice;
    private String caption;
    private Contact contact;
    private Location location;
    private Venue venue;
    private User new_chat_member;
    private User left_chat_member;
    private String new_chat_title;
    private List<PhotoSize> new_chat_photo = new ArrayList<PhotoSize>();
    private boolean delete_chat_photo;
    private boolean group_chat_created;
    private boolean supergroup_chat_created;
    private boolean channel_chat_created;
    private long migrate_to_chat_id;
    private long migrate_from_chat_id;
    private Message pinned_message;

    private int updateId;

    public long getMessageId() {
        return message_id;
    }

    public void setMessageId(long messageId) {
        this.message_id = messageId;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getForwardFrom() {
        return forward_from;
    }

    public void setForwardFrom(User forwardMessage) {
        this.forward_from = forwardMessage;
    }

    public int getForwardDate() {
        return forward_date;
    }

    public void setForwardDate(int forwardDate) {
        this.forward_date = forwardDate;
    }

    public Message getReplyToMessage() {
        return reply_to_message;
    }

    public void setReplyToMessage(Message replyToMessage) {
        this.reply_to_message = replyToMessage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<PhotoSize> getPhoto() {
        return photo;
    }

    public void setPhoto(List<PhotoSize> photo) {
        this.photo = photo;
    }

    public Sticker getSticker() {
        return sticker;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getNewChatParticipant() {
        return new_chat_member;
    }

    public void setNewChatParticipant(User newChatParticipant) {
        this.new_chat_member = newChatParticipant;
    }

    public User getLeftChatParticipant() {
        return left_chat_member;
    }

    public void setLeftChatParticipant(User leftChatParticipant) {
        this.left_chat_member = leftChatParticipant;
    }

    public String getNewChatTitle() {
        return new_chat_title;
    }

    public void setNewChatTitle(String newChatTitle) {
        this.new_chat_title = newChatTitle;
    }

    public List<PhotoSize> getNewChatPhoto() {
        return new_chat_photo;
    }

    public void setNewChatPhoto(List<PhotoSize> newChatPhoto) {
        this.new_chat_photo = newChatPhoto;
    }

    public boolean isDeleteChatPhoto() {
        return delete_chat_photo;
    }

    public void setDeleteChatPhoto(boolean deleteChatPhoto) {
        this.delete_chat_photo = deleteChatPhoto;
    }

    public boolean isGroupChatCreated() {
        return group_chat_created;
    }

    public void setGroupChatCreated(boolean groupChatCreated) {
        this.group_chat_created = groupChatCreated;
    }

    public boolean isSupergroup_chat_created() {
        return supergroup_chat_created;
    }

    public void setSupergroup_chat_created(boolean supergroup_chat_created) {
        this.supergroup_chat_created = supergroup_chat_created;
    }

    public boolean isChannel_chat_created() {
        return channel_chat_created;
    }

    public void setChannel_chat_created(boolean channel_chat_created) {
        this.channel_chat_created = channel_chat_created;
    }

    public long getMigrate_to_chat_id() {
        return migrate_to_chat_id;
    }

    public void setMigrate_to_chat_id(long migrate_to_chat_id) {
        this.migrate_to_chat_id = migrate_to_chat_id;
    }

    public long getMigrate_from_chat_id() {
        return migrate_from_chat_id;
    }

    public void setMigrate_from_chat_id(long migrate_from_chat_id) {
        this.migrate_from_chat_id = migrate_from_chat_id;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public Chat getForward_from_chat() {
        return forward_from_chat;
    }

    public void setForward_from_chat(Chat forward_from_chat) {
        this.forward_from_chat = forward_from_chat;
    }

    public int getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(int edit_date) {
        this.edit_date = edit_date;
    }

    public List<MessageEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<MessageEntity> entities) {
        this.entities = entities;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Message getPinned_message() {
        return pinned_message;
    }

    public void setPinned_message(Message pinned_message) {
        this.pinned_message = pinned_message;
    }
}
