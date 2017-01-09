
package cimidentities.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "SendToDB", namespace = "http://cimidentities/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendToDB", namespace = "http://cimidentities/", propOrder = {
    "nName",
    "ntName",
    "ntDes",
    "ntaName",
    "ntaDes",
    "uuid",
    "uuidEntered"
})
public class SendToDB {

    @XmlElement(name = "n_name", namespace = "")
    private String nName;
    @XmlElement(name = "nt_name", namespace = "")
    private String ntName;
    @XmlElement(name = "nt_des", namespace = "")
    private String ntDes;
    @XmlElement(name = "nta_name", namespace = "")
    private String ntaName;
    @XmlElement(name = "nta_des", namespace = "")
    private String ntaDes;
    @XmlElement(name = "uuid", namespace = "")
    private String uuid;
    @XmlElement(name = "uuidEntered", namespace = "")
    private boolean uuidEntered;

    /**
     * 
     * @return
     *     returns String
     */
    public String getNName() {
        return this.nName;
    }

    /**
     * 
     * @param nName
     *     the value for the nName property
     */
    public void setNName(String nName) {
        this.nName = nName;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getNtName() {
        return this.ntName;
    }

    /**
     * 
     * @param ntName
     *     the value for the ntName property
     */
    public void setNtName(String ntName) {
        this.ntName = ntName;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getNtDes() {
        return this.ntDes;
    }

    /**
     * 
     * @param ntDes
     *     the value for the ntDes property
     */
    public void setNtDes(String ntDes) {
        this.ntDes = ntDes;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getNtaName() {
        return this.ntaName;
    }

    /**
     * 
     * @param ntaName
     *     the value for the ntaName property
     */
    public void setNtaName(String ntaName) {
        this.ntaName = ntaName;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getNtaDes() {
        return this.ntaDes;
    }

    /**
     * 
     * @param ntaDes
     *     the value for the ntaDes property
     */
    public void setNtaDes(String ntaDes) {
        this.ntaDes = ntaDes;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 
     * @param uuid
     *     the value for the uuid property
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 
     * @return
     *     returns boolean
     */
    public boolean isUuidEntered() {
        return this.uuidEntered;
    }

    /**
     * 
     * @param uuidEntered
     *     the value for the uuidEntered property
     */
    public void setUuidEntered(boolean uuidEntered) {
        this.uuidEntered = uuidEntered;
    }

}
