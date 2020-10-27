package Model;
//Solution from https://stackoverflow.com/questions/15607969/appending-objects-to-a-serialization-file

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/*When appending to file, new headers are generated on every append. The file appears to be corrupted when reading from it,
making the data irretrievable. To prevent that, the output stream can be overriden.
 */
public class AppendObjectOutputStream extends ObjectOutputStream {

    public AppendObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    public void writeStreamHeader() throws IOException {
        reset();
    }
}
