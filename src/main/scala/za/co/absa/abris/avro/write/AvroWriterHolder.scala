/*
 * Copyright 2018 ABSA Group Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package za.co.absa.abris.avro.write

import java.io.ByteArrayOutputStream

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericDatumWriter, IndexedRecord}
import org.apache.avro.io.{BinaryEncoder, DatumWriter, Encoder, EncoderFactory}

/**
 * Holds an Avro writer. Convenient for encapsulating logic behind object reuse.
 */
class AvroWriterHolder {
     
  def getWriter(schema: Schema): DatumWriter[IndexedRecord] = {
    this.writer.setSchema(schema)
    this.writer
  }

  def getStringWriter(schema: Schema): GenericDatumWriter[String] = {
    this.stringWriter.setSchema(schema)
    this.stringWriter
  }

  def getEncoder(outStream: ByteArrayOutputStream): Encoder = {       
    if (encoder == null) {     
     encoder = EncoderFactory.get().binaryEncoder(outStream, null)
    }
    EncoderFactory.get().binaryEncoder(outStream, encoder)
  }
    
  private val writer = new ScalaCustomDatumWriter[IndexedRecord]()
  private val stringWriter = new GenericDatumWriter[String]
  private var encoder: BinaryEncoder = null
}