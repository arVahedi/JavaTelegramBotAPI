package api.requestobject;

import api.entity.InputFile;

import java.io.Serializable;

/**
 * Created by Gladiator on 2/26/2016 AD.
 */
public class RequestSetWebHook implements Serializable{
    //region Fields
    private String url;
    private InputFile certificate;
    //endregion

    //region Constructors
    public RequestSetWebHook() {
    }

    public RequestSetWebHook(String url){
        this.url = url;
    }

    public RequestSetWebHook(InputFile certificate){
        this.certificate = certificate;
    }

    public RequestSetWebHook(String url, InputFile certificate) {
        this.url = url;
        this.certificate = certificate;
    }
    //endregion

    //region Getter and Setter
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
    //endregion
}
