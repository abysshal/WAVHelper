package sh.dreamingfi.utils.pcm2wav;

public class WAVHeader {

    public boolean inited = false;
    private String chunkID = null;
    private int chunckSize = 0;
    private String format = null;
    private String subChunk1ID = null;
    private int subChunk1Size = 16;
    private short audioFormat = 1;
    private short numChannels = 1;
    private int sampleRate = 8000;
    private int byteRate = 0;
    private short blockAlign = 0;
    private short bitsPerSample = 8;
    private String subChunk2ID = null;
    private int subChunk2Size = 0;
    private byte[] data;

    public boolean parseHeader(byte[] bytes) {
        if (bytes == null || bytes.length < 44) {
            return false;
        }

        // starts with 'RIFF' in ascii
        this.chunkID = new String(bytes, 0, 4);
        if (!this.chunkID.equals("RIFF")) {
            return false;
        }

        this.chunckSize = BinaryUtil.byte2int_little(bytes, 4);

        this.format = new String(bytes, 8, 4);
        if (!this.format.equals("WAVE")) {
            return false;
        }

        this.subChunk1ID = new String(bytes, 12, 4);
        if (!this.subChunk1ID.equals("fmt ")) {
            return false;
        }

        this.subChunk1Size = BinaryUtil.byte2int_little(bytes, 16);
        this.audioFormat = BinaryUtil.byte2short_little(bytes, 20);
        this.numChannels = BinaryUtil.byte2short_little(bytes, 22);
        this.sampleRate = BinaryUtil.byte2int_little(bytes, 24);
        this.byteRate = BinaryUtil.byte2int_little(bytes, 28);
        this.blockAlign = BinaryUtil.byte2short_little(bytes, 32);
        this.bitsPerSample = BinaryUtil.byte2short_little(bytes, 34);

        // this.subChunk2ID = new String(bytes, 36, 4);
        // if(!subChunk2ID.equals("data")){
        // return false;
        // }

        // this.subChunk2Size = BinaryUtil.byte2int_little(bytes, 40);

        this.inited = true;
        return inited;
    }

    public void writeHeader(byte[] bytes, int offset) {
        this.byteRate = this.sampleRate * this.numChannels * this.bitsPerSample / 8;
        this.blockAlign = (short) (this.numChannels * this.bitsPerSample / 8);
        this.chunckSize = this.subChunk2Size + 36;

        System.arraycopy("RIFF".getBytes(), 0, bytes, offset, 4);
        BinaryUtil.int2byte_little(this.chunckSize, bytes, offset + 4);
        System.arraycopy("WAVEfmt ".getBytes(), 0, bytes, offset + 8, 8);
        BinaryUtil.int2byte_little(this.subChunk1Size, bytes, offset + 16);
        BinaryUtil.short2byte_little(this.audioFormat, bytes, offset + 20);
        BinaryUtil.short2byte_little(this.numChannels, bytes, offset + 22);
        BinaryUtil.int2byte_little(this.sampleRate, bytes, offset + 24);
        BinaryUtil.int2byte_little(this.byteRate, bytes, offset + 28);
        BinaryUtil.short2byte_little(this.blockAlign, bytes, offset + 32);
        BinaryUtil.short2byte_little(this.bitsPerSample, bytes, offset + 34);
        System.arraycopy("data".getBytes(), 0, bytes, offset + 36, 4);
        BinaryUtil.int2byte_little(this.subChunk2Size, bytes, offset + 40);
    }

    public String printFormat() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.format);
        stringBuilder.append(":\nNumberOfChannels:");
        stringBuilder.append(this.numChannels);
        stringBuilder.append("\nSampleRate:");
        stringBuilder.append(this.sampleRate);
        stringBuilder.append("\nByteRate:");
        stringBuilder.append(this.byteRate);
        stringBuilder.append("\nBlockAlign:");
        stringBuilder.append(this.blockAlign);
        stringBuilder.append("\nBitsPerSample:");
        stringBuilder.append(this.bitsPerSample);
        stringBuilder.append("\nDataSize:");
        stringBuilder.append(this.subChunk2Size);
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    public short getNumChannels() {
        return numChannels;
    }

    public void setNumChannels(short numChannels) {
        this.numChannels = numChannels;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public short getBitsPerSample() {
        return bitsPerSample;
    }

    public void setBitsPerSample(short bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }

    public int getSubChunk2Size() {
        return subChunk2Size;
    }

    public void setSubChunk2Size(int subChunk2Size) {
        this.subChunk2Size = subChunk2Size;
    }
}
