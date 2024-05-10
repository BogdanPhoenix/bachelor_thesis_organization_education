package com.bachelor.thesis.organization_education.services.implementations.tools;

import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import com.bachelor.thesis.organization_education.exceptions.FileException;

public class StorageTools {
    private StorageTools() {}

    public static byte[] compressFile(byte[] data) throws FileException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            var buffer = new byte[4 * 1024];
            var deflater = new Deflater();
            deflater.setLevel(Deflater.BEST_COMPRESSION);
            deflater.setInput(data);
            deflater.finish();

            while (!deflater.finished()) {
                var size = deflater.deflate(buffer);
                outputStream.write(buffer, 0, size);
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new FileException("Error compressing file.", e);
        }
    }

    public static byte[] decompressFile(byte[] data) {
        try (var outputStream = new ByteArrayOutputStream(data.length)) {
            var buffer = new byte[4 * 1024];
            var inflater = new Inflater();
            inflater.setInput(data);

            while (!inflater.finished()) {
                var count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }

            return outputStream.toByteArray();
        } catch (DataFormatException e) {
            throw new FileException("Invalid compressed data format.", e);
        } catch (Exception e) {
            throw new FileException("Error decompressing file.", e);
        }
    }
}
