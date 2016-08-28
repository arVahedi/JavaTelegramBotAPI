package api.requestobject;

import api.entity.InputFile;

/**
 * Created by Gladiator on 2/26/2016 AD.
 */
public class RequestSetWebHook {
    private String url;
    private InputFile certificate;

    public RequestSetWebHook() {
    }

    public RequestSetWebHook(String url, InputFile certificate) {
        this.url = url;
        this.certificate = certificate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputFile getCertificate() {
        return certificate;
    }

    public void setCertificate(InputFile certificate) {
        this.certificate = certificate;
    }
}
