/*
* Copyright 2014 The Netty Project
*
* The Netty Project licenses this file to you under the Apache License,
* version 2.0 (the "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at:
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/
package io.netty.microbench.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.microbench.util.AbstractMicrobenchmark;
import io.netty.util.CharsetUtil;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;


@State(Scope.Benchmark)
@Warmup(iterations = 10)
@Measurement(iterations = 25)
public class ByteBufUtilBenchmark extends AbstractMicrobenchmark {
    private ByteBuf buffer;
    private ByteBuf wrapped;

    private StringBuilder asciiSequence;
    private String ascii;

    private StringBuilder utf8Sequence;
    private String utf8;

    @Setup
    public void setup() {
        // Use buffer sizes that will also allow to write UTF-8 without grow the buffer
        buffer = Unpooled.directBuffer(512);
        wrapped = Unpooled.unreleasableBuffer(Unpooled.directBuffer(512));
        asciiSequence = new StringBuilder(128);
        for (int i = 0; i < 128; i++) {
            asciiSequence.append('a');
        }
        ascii = asciiSequence.toString();

        // Generate some mixed UTF-8 String for benchmark
        utf8Sequence = new StringBuilder(128);
        char[] chars = "Some UTF-8 like äÄ∏ŒŒ".toCharArray();
        for (int i = 0; i < 128; i++) {
            utf8Sequence.append(chars[i % chars.length]);
        }
        utf8 = utf8Sequence.toString();
        asciiSequence = utf8Sequence;
    }

    @TearDown
    public void tearDown() {
        buffer.release();
        wrapped.release();
    }

    @Benchmark
    public void writeAsciiStringViaArray() {
        buffer.resetWriterIndex();
        buffer.writeBytes(ascii.getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeAsciiStringViaArrayWrapped() {
        wrapped.resetWriterIndex();
        wrapped.writeBytes(ascii.getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeAsciiString() {
        buffer.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(buffer, ascii);
    }

    @Benchmark
    public void writeAsciiStringWrapped() {
        wrapped.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(wrapped, ascii);
    }

    @Benchmark
    public void writeAsciiCharSequenceViaArray() {
        buffer.resetWriterIndex();
        buffer.writeBytes(asciiSequence.toString().getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeAsciiCharSequenceViaArrayWrapped() {
        wrapped.resetWriterIndex();
        wrapped.writeBytes(asciiSequence.toString().getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeAsciiCharSequence() {
        buffer.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(buffer, asciiSequence);
    }

    @Benchmark
    public void writeAsciiCharSequenceWrapped() {
        wrapped.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(wrapped, asciiSequence);
    }

    @Benchmark
    public void writeUtf8StringViaArray() {
        buffer.resetWriterIndex();
        buffer.writeBytes(utf8.getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeUtf8StringViaArrayWrapped() {
        wrapped.resetWriterIndex();
        wrapped.writeBytes(utf8.getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeUtf8String() {
        buffer.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(buffer, utf8);
    }

    @Benchmark
    public void writeUtf8StringWrapped() {
        wrapped.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(wrapped, utf8);
    }

    @Benchmark
    public void writeUtf8CharSequenceViaArray() {
        buffer.resetWriterIndex();
        buffer.writeBytes(utf8Sequence.toString().getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeUtf8CharSequenceViaArrayWrapped() {
        wrapped.resetWriterIndex();
        wrapped.writeBytes(utf8Sequence.toString().getBytes(CharsetUtil.US_ASCII));
    }

    @Benchmark
    public void writeUtf8CharSequence() {
        buffer.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(buffer, utf8Sequence);
    }

    @Benchmark
    public void writeUtf8CharSequenceWrapped() {
        wrapped.resetWriterIndex();
        ByteBufUtil.writeAsciiCharSequence(wrapped, utf8Sequence);
    }
}
