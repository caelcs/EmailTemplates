package com.dods.mpp.domain.document;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(namespace = "http://dods.dodsXSLMapping", name = "dodsXSLMapping")
public class XslMapping {
    private List<XslMappingElement> xslMappings;

    @XmlElements(@XmlElement(name="XSLMapping", type = XslMappingElement.class))
    public List<XslMappingElement> getXslMappings() {
        return this.xslMappings;
    }

    public void setXslMappings(List<XslMappingElement> xslMappings) {
        this.xslMappings = xslMappings;
    }

    public static class XslMappingElement {
        private String payloadType;
        private String schemaType;
        private ContentProvenanceElement contentProvenance;
        private String xslName;
        private String tolerance;

        @XmlElement(name="PayloadType")
        public String getPayloadType() {
            return payloadType;
        }

        public void setPayloadType(String payloadType) {
            this.payloadType = payloadType;
        }

        @XmlElement(name="SchemaType")
        public String getSchemaType() {
            return schemaType;
        }

        public void setSchemaType(String schemaType) {
            this.schemaType = schemaType;
        }

        @XmlElement(name="ContentProvenance")
        public ContentProvenanceElement getContentProvenance() {
            return contentProvenance;
        }

        public void setContentProvenance(ContentProvenanceElement contentProvenance) {
            this.contentProvenance = contentProvenance;
        }

        @XmlElement(name="XSL")
        public String getXslName() {
            return xslName;
        }

        public void setXslName(String xslName) {
            this.xslName = xslName;
        }

        @XmlElement(name="Tolerance")
        public String getTolerance() {
            return tolerance;
        }

        public void setTolerance(String tolerance) {
            this.tolerance = tolerance;
        }
    }

    public static class ContentProvenanceElement {
        private String contentSource;
        private String contentLocation;
        private String informationType;
        private String startXpath;
        private String endXpath;

        @XmlElement(name="InformationType")
        public String getInformationType() {
            return informationType;
        }

        public void setInformationType(String informationType) {
            this.informationType = informationType;
        }

        @XmlElement(name="ContentSource")
        public String getContentSource() {
            return contentSource;
        }

        public void setContentSource(String contentSource) {
            this.contentSource = contentSource;
        }

        @XmlElement(name="ContentLocation")
        public String getContentLocation() {
            return contentLocation;
        }

        public void setContentLocation(String contentLocation) {
            this.contentLocation = contentLocation;
        }

        @XmlElement(name="StartXpath")
        public String getStartXpath() {
            return startXpath;
        }

        public void setStartXpath(String startXpath) {
            this.startXpath = startXpath;
        }

        @XmlElement(name="EndXpath")
        public String getEndXpath() {
            return endXpath;
        }

        public void setEndXpath(String endXpath) {
            this.endXpath = endXpath;
        }
    }

}