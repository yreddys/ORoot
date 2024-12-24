package com.root.admin.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;

public class BlobToBase64Serializer extends JsonSerializer<Blob> {

    @Override
    public void serialize(Blob blob, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (blob != null) {
            try {
                byte[] photoBytes = blob.getBytes(1, (int) blob.length());
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                gen.writeString(base64Photo);
            } catch (Exception e) {
                gen.writeNull();
            }
        } else {
            gen.writeNull();
        }
    }
}

