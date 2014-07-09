/**
 * 
 */
package org.archive.wayback.replay;

import java.io.IOException;
import java.util.Map;

import org.archive.wayback.core.Resource;

/**
 * Virtual {@link Resource} made up from pair of header resource
 * and payload resource.
 * This class is typically used for binding revisit record and
 * revisited (original) resource together, making it look like
 * one complete capture.
 */
public class CompositeResource extends Resource {
	private final Resource headersResource;
	private final Resource payloadResource;
	/**
	 * constructor.
	 * @param headersResource Resource providing HTTP heeaders
	 * (revisit record).
	 * @param payloadResource Resource providing HTTP response entity
	 * (revisited original record).
	 */
	public CompositeResource(Resource headersResource, Resource payloadResource) {
		this.headersResource = headersResource;
		this.payloadResource = payloadResource;
	}
	/* (non-Javadoc)
	 * @see org.archive.wayback.core.Resource#close()
	 */
	@Override
	public void close() throws IOException {
		// TODO: should call close on both?
		payloadResource.close();
	}

	/* (non-Javadoc)
	 * @see org.archive.wayback.core.Resource#getStatusCode()
	 */
	@Override
	public int getStatusCode() {
		return headersResource.getStatusCode();
	}

	/* (non-Javadoc)
	 * @see org.archive.wayback.core.Resource#getRecordLength()
	 */
	@Override
	public long getRecordLength() {
		return payloadResource.getRecordLength();
	}

	/* (non-Javadoc)
	 * @see org.archive.wayback.core.Resource#getHttpHeaders()
	 */
	@Override
	public Map<String, String> getHttpHeaders() {
		// TODO: if headerResource has no HTTP headers (old
		// revisit WARC record), get it from payloadResource.
		return headersResource.getHttpHeaders();
	}
	@Override
	public void parseHeaders() throws IOException {
		headersResource.parseHeaders();
		payloadResource.parseHeaders();
	}
	@Override
	public String getHeader(String headerName) {
		return headersResource.getHeader(headerName);
	}
	@Override
	public void setChunkedEncoding() throws IOException {
		payloadResource.setChunkedEncoding();
	}
	@Override
	public int available() throws IOException {
		return payloadResource.available();
	}
	@Override
	public void mark(int readlimit) {
		payloadResource.mark(readlimit);
	}
	@Override
	public boolean markSupported() {
		return payloadResource.markSupported();
	}
	@Override
	public int read() throws IOException {
		return payloadResource.read();
	}
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return payloadResource.read(b, off, len);
	}
	@Override
	public int read(byte[] b) throws IOException {
		return payloadResource.read(b);
	}
	@Override
	public void reset() throws IOException {
		payloadResource.reset();
	}
	@Override
	public long skip(long n) throws IOException {
		return payloadResource.skip(n);
	}
}
