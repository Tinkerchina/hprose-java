/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * DoubleFieldAccessor.java                               *
 *                                                        *
 * DoubleFieldAccessor class for Java.                    *
 *                                                        *
 * LastModified: Jun 24, 2015                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/
package hprose.io.accessor;

import hprose.common.HproseException;
import hprose.io.serialize.HproseWriter;
import hprose.io.serialize.ValueWriter;
import hprose.io.unserialize.DoubleUnserializer;
import hprose.io.unserialize.HproseReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public final class DoubleFieldAccessor implements MemberAccessor {
    private final long offset;

    public DoubleFieldAccessor(Field accessor) {
        accessor.setAccessible(true);
        offset = Accessors.unsafe.objectFieldOffset(accessor);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void serialize(HproseWriter writer, Object obj) throws IOException {
        double value;
        try {
            value = Accessors.unsafe.getDouble(obj, offset);
        }
        catch (Exception e) {
            throw new HproseException(e.getMessage());
        }
        ValueWriter.write(writer.stream, value);
    }

    @Override
    public void unserialize(HproseReader reader, ByteBuffer buffer, Object obj) throws IOException {
        double value = DoubleUnserializer.read(reader, buffer);
        try {
            Accessors.unsafe.putDouble(obj, offset, value);
        }
        catch (Exception e) {
            throw new HproseException(e.getMessage());
        }
    }

    @Override
    public void unserialize(HproseReader reader, InputStream stream, Object obj) throws IOException {
        double value = DoubleUnserializer.read(reader, stream);
        try {
            Accessors.unsafe.putDouble(obj, offset, value);
        }
        catch (Exception e) {
            throw new HproseException(e.getMessage());
        }
    }
}