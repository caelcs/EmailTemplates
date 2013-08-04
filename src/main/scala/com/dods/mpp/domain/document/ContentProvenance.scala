package com.dods.mpp.domain.document

import reflect.BeanProperty

class ContentProvenance {
    @BeanProperty var contentSource: String = _
    @BeanProperty var contentLocation: String = _
    @BeanProperty var informationType: String = _
}

object ContentProvenance {
    def apply(contentSource: String, contentLocation: String, informationType: String) = {
        val obj = new ContentProvenance
        obj.contentLocation = contentLocation
        obj.contentSource = contentSource
        obj.informationType = informationType
        obj
    }
}
