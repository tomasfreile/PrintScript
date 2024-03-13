package org.example.interpreter.operation

class PlusOperation: Operation {
    override val symbol = "+"

    override fun resolve(l: Any?, r: Any?): Any {
        if(l is Int && r is Int){
            return l + r
        }
        if( l is String){
            return l + r.toString()
        }
        throw UnsupportedOperationException("Unsupported type for plus operation")
    }
}