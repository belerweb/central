package com.belerweb.central.dao;

import org.bson.types.Binary;

import com.googlecode.mjorm.convert.ConversionContext;
import com.googlecode.mjorm.convert.ConversionException;
import com.googlecode.mjorm.convert.JavaType;
import com.googlecode.mjorm.convert.TypeConversionHints;
import com.googlecode.mjorm.convert.TypeConverter;

public class ByteArrayToBinary implements TypeConverter<byte[], Binary> {

  @Override
  public boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
    return byte[].class.isAssignableFrom(sourceClass) && Binary.class.isAssignableFrom(targetClass);
  }

  @Override
  public Binary convert(byte[] source, JavaType targetType, ConversionContext context,
      TypeConversionHints hints) throws ConversionException {
    return new Binary(source);
  }

}
