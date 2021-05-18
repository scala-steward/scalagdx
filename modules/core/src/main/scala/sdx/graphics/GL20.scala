/**
 * Copyright (c) 2021 scalagdx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This product includes software developed at libGDX (https://libgdx.com/).
 */
package sdx.graphics

import java.nio.Buffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * Wrapper for all the methods of OpenGL ES 2.0
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
trait GL20[F[_]] {
  def glActiveTexture(texture: Int): F[Unit]

  def glBindTexture(target: Int, texture: Int): F[Unit]

  def glClear(mask: Int): F[Unit]

  def glClearColor(r: Float, g: Float, b: Float, a: Float): F[Unit]

  def glClearDepthf(depth: Float): F[Unit]

  def glClearStencil(s: Int): F[Unit]

  def glColorMask(r: Float, g: Float, b: Float, a: Float): F[Unit]

  def glCompressedTexImage2D(
      target: Int,
      level: Int,
      internalFormat: Int,
      width: Int,
      height: Int,
      format: Int,
      imageSize: Int,
      data: Buffer,
  ): F[Unit]

  def glCompressedTexSubImage2D(
      target: Int,
      level: Int,
      xOffset: Int,
      yOffset: Int,
      width: Int,
      height: Int,
      format: Int,
      imageSize: Int,
      data: Buffer,
  ): F[Unit]

  def glCopyTexImage2D(target: Int, level: Int, intervalFormat: Int, x: Int, y: Int, width: Int, border: Int): F[Unit]

  def glCopyTexSubImage2D(
      target: Int,
      level: Int,
      xOffset: Int,
      yOffset: Int,
      x: Int,
      y: Int,
      width: Int,
      height: Int,
  ): F[Unit]

  def glCullFace(mode: Int): F[Unit]

  def glDeleteTextures(n: Int, textures: IntBuffer): F[Unit]

  def glDeleteTexture(texture: Int): F[Unit]

  def glDepthFunc(func: Int): F[Unit]

  def glDepthMask(flag: Boolean): F[Unit]

  def glDepthRangef(zNear: Float, zFar: Float): F[Unit]

  def glDisable(cap: Int): F[Unit]

  def glDrawArrays(mode: Int, first: Int, count: Int): F[Unit]

  def glDrawElements(mode: Int, count: Int, `type`: Int, indices: Buffer): F[Unit]

  def glEnable(cap: Int): F[Unit]

  def glFinish: F[Unit]

  def glFlush: F[Unit]

  def glFrontFace(mode: Int): F[Unit]

  def glGenTextures(n: Int, textures: IntBuffer): F[Unit]

  def glGenTexture: F[Int]

  def glGetError: F[Int]

  def glGetIntegerv(pname: Int, params: IntBuffer): F[Unit]

  def glGetString(name: Int): F[String]

  def glHint(target: Int, mode: Int): F[Unit]

  def glLineWidth(width: Float): F[Unit]

  def glPixelStorei(pname: Int, param: Int): F[Unit]

  def glPolygonOffset(factor: Float, units: Float): F[Unit]

  def glReadPixels(x: Int, y: Int, width: Int, height: Int, format: Int, `type`: Int, pixels: Buffer): F[Unit]

  def glScissor(x: Int, y: Int, width: Int, height: Int): F[Unit]

  def glStencilFunc(func: Int, ref: Int, mask: Int): F[Unit]

  def glStencilMask(mask: Int): F[Unit]

  def glStencilOp(fail: Int, zfail: Int, zpass: Int): F[Unit]

  def glTexImage2D(
      target: Int,
      level: Int,
      internalFormat: Int,
      width: Int,
      height: Int,
      border: Int,
      format: Int,
      `type`: Int,
      pixels: Buffer,
  ): F[Unit]

  def glTexParameterf(target: Int, pname: Int, param: Float): F[Unit]

  def glTexSubImage2D(
      target: Int,
      level: Int,
      xOffset: Int,
      yOffset: Int,
      width: Int,
      height: Int,
      format: Int,
      `type`: Int,
      pixels: Buffer,
  ): F[Unit]

  def glViewport(x: Int, y: Int, width: Int, height: Int): F[Unit]

  def glAttachShader(program: Int, shader: Int): F[Unit]

  def glBindAttribLocation(program: Int, index: Int, name: String): F[Unit]

  def glBindBuffer(target: Int, buffer: Int): F[Unit]

  def glBindFramebuffer(target: Int, framebuffer: Int): F[Unit]

  def glBindRenderbuffer(target: Int, renderbuffer: Int): F[Unit]

  def glBlendColor(r: Float, g: Float, b: Float, a: Float): F[Unit]

  def glBlendEquation(mode: Int): F[Unit]

  def glBlendEquationSeparate(modeRGB: Int, modeAlpha: Int): F[Unit]

  def glBlendFuncSeparate(srcRGB: Int, dstRGB: Int, srcAlpha: Int, dstAlpha: Int): F[Unit]

  def glBufferData(target: Int, size: Int, data: Buffer, usage: Int): F[Unit]

  def glBufferSubData(target: Int, offset: Int, size: Int, data: Buffer): F[Unit]

  def glCheckFramebufferStatus(target: Int): F[Int]

  def glCompileShader(shader: Int): F[Unit]

  def glCreateProgram: F[Int]

  def glCreateShader(`type`: Int): F[Int]

  def glDeleteBuffer(buffer: Int): F[Unit]

  def glDeleteBuffers(n: Int, buffers: IntBuffer): F[Unit]

  def glDeleteFramebuffer(framebuffer: Int): F[Unit]

  def glDeleteFramebuffers(n: Int, framebuffers: IntBuffer): F[Unit]

  def glDeleteProgram(program: Int): F[Unit]

  def glDeleteRenderbuffer(renderbuffer: Int): F[Unit]

  def glDeleteRenderbuffers(n: Int, renderbuffers: IntBuffer): F[Unit]

  def glDeleteShader(shader: Int): F[Unit]

  def glDetachShader(program: Int, shader: Int): F[Unit]

  def glDisableVertexAttribArray(index: Int): F[Unit]

  def glDrawElements(mode: Int, count: Int, `type`: Int, indices: Int): F[Unit]

  def glEnableVertexAttribArray(index: Int): F[Unit]

  def glFramebufferRenderbuffer(target: Int, attachment: Int, renderbuffertarget: Int, renderbuffer: Int): F[Unit]

  def glFramebufferTexture2D(target: Int, attachment: Int, textarget: Int, texture: Int, level: Int): F[Unit]

  def glGenBuffer: F[Int]

  def glGenBuffers(n: Int, buffers: IntBuffer): F[Unit]

  def glGenerateMipmap(target: Int): F[Unit]

  def glGenFramebuffer: F[Int]

  def glGenFramebuffers(n: Int, framebuffers: IntBuffer): F[Unit]

  def glGenRenderbuffer: F[Int]

  def glGenRenderbuffers(n: Int, renderbuffers: IntBuffer): F[Unit]

  def glGetActiveAttrib(program: Int, index: Int, size: IntBuffer, `type`: IntBuffer): F[String]

  def glGetActiveUniform(program: Int, index: Int, size: IntBuffer, `type`: IntBuffer): F[String]

  def glGetAttachedShaders(program: Int, maxcount: Int, count: Buffer, shaders: IntBuffer): F[Unit]

  def glGetAttribLocation(program: Int, name: String): F[Int]

  def glBooleanv(pname: Int, params: Buffer): F[Unit]

  def glGetBufferParameteriv(target: Int, pname: Int, params: IntBuffer): F[Unit]

  def glGetFloatv(pname: Int, params: FloatBuffer): F[Unit]

  def glGetFramebufferAttachmentParameteriv(target: Int, attachment: Int, pname: Int, params: IntBuffer): F[Unit]

  def glGetProgramiv(program: Int, pname: Int, params: IntBuffer): F[Unit]

  def glGetProgramInfoLog(program: Int): F[String]

  def glGetRenderbufferParameteriv(target: Int, pname: Int, params: IntBuffer): F[Unit]

  def glGetShaderiv(shader: Int, pname: Int, params: IntBuffer): F[Unit]

  def glGetShaderInfoLog(shader: Int): F[String]

  def glGetShaderPrecisionFormat(shaderType: Int, precisionType: Int, range: IntBuffer, precision: IntBuffer): F[Unit]

  def glGetTexParameterfv(target: Int, pname: Int, params: FloatBuffer): F[Unit]

  def glGetTexParameteriv(target: Int, pname: Int, params: IntBuffer): F[Unit]

  def glGetUniformfv(program: Int, location: Int, params: FloatBuffer): F[Unit]

  def glGetUniformfv(program: Int, location: Int, params: IntBuffer): F[Unit]

  def glGetUniformLocation(program: Int, name: String): F[Int]

  def glGetVertexAttribfv(index: Int, pname: Int, params: FloatBuffer): F[Unit]

  def glGetVertexAttribiv(index: Int, pname: Int, params: IntBuffer): F[Unit]

  def glGetVertexAttribPointerv(index: Int, pname: Int, pointer: Buffer): F[Unit]

  def glIsBuffer(buffer: Int): F[Boolean]

  def glIsEnabled(cap: Int): F[Boolean]

  def glIsFramebuffer(framebuffer: Int): F[Boolean]

  def glIsProgram(program: Int): F[Boolean]

  def glIsRenderbuffer(renderbuffer: Int): F[Boolean]

  def glIsShader(shader: Int): F[Boolean]

  def glIsTexture(texture: Int): F[Boolean]

  def glLinkProgram(program: Int): F[Unit]

  def glReleaseShaderCompiler: F[Unit]

  def glRenderbufferStorage(target: Int, internalFormat: Int, width: Int, height: Int): F[Unit]

  def glSampleCoverage(value: Float, invert: Boolean): F[Unit]

  def glShaderBinary(n: Int, shader: IntBuffer, binaryFormat: Int, binary: Buffer, length: Int): F[Unit]

  def glShaderSource(shader: Int, string: String): F[Unit]

  def glStencilFuncSeparate(face: Int, func: Int, ref: Int, mask: Int): F[Unit]

  def glStencilMaskSeparate(face: Int, mask: Int): F[Unit]

  def glStencilOpSeparate(face: Int, fail: Int, zfail: Int, zpass: Int): F[Unit]

  def glTexParameterfv(target: Int, pname: Int, params: FloatBuffer): F[Unit]

  def glTexParameteri(target: Int, pname: Int, param: Int): F[Unit]

  def glTexParameteriv(target: Int, pname: Int, params: IntBuffer): F[Unit]

  def glUniform1f(location: Int, x: Float): F[Unit]

  def glUniform1fv(location: Int, count: Int, v: FloatBuffer): F[Unit]

  def glUniform1fv(location: Int, count: Int, v: Array[Float], offset: Int): F[Unit]

  def glUniform1i(location: Int, x: Int): F[Unit]

  def glUniform1iv(location: Int, count: Int, v: IntBuffer): F[Unit]

  def glUniform1iv(location: Int, count: Int, v: Array[Int], offset: Int): F[Unit]

  def glUniform2f(location: Int, x: Float, y: Float): F[Unit]

  def glUniform2fv(location: Int, count: Int, v: FloatBuffer): F[Unit]

  def glUniform2fv(location: Int, count: Int, v: Array[Float], offset: Int): F[Unit]

  def glUniform2i(location: Int, x: Int, y: Int): F[Unit]

  def glUniform2iv(location: Int, count: Int, v: IntBuffer): F[Unit]

  def glUniform2iv(location: Int, count: Int, v: Array[Int], offset: Int): F[Unit]

  def glUniform3f(location: Int, x: Float, y: Float, z: Float): F[Unit]

  def glUniform3fv(location: Int, count: Int, v: FloatBuffer): F[Unit]

  def glUniform3fv(location: Int, count: Int, v: Array[Float], offset: Int): F[Unit]

  def glUniform3i(location: Int, x: Int, y: Int, z: Int): F[Unit]

  def glUniform3iv(location: Int, count: Int, v: IntBuffer): F[Unit]

  def glUniform3iv(location: Int, count: Int, v: Array[Int], offset: Int): F[Unit]

  def glUniform4f(location: Int, x: Float, y: Float, z: Float, w: Float): F[Unit]

  def glUniform4fv(location: Int, count: Int, v: FloatBuffer): F[Unit]

  def glUniform4fv(location: Int, count: Int, v: Array[Int], offset: Int): F[Unit]

  def glUniform4i(location: Int, x: Int, y: Int, z: Int, w: Int): F[Unit]

  def glUniform4iv(location: Int, count: Int, v: IntBuffer): F[Unit]

  def glUniform4iv(location: Int, count: Int, v: Array[Int], offset: Int): F[Unit]

  def glUniformMatrix2fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer): F[Unit]

  def glUniformMatrix2fv(location: Int, count: Int, transpose: Boolean, value: Array[Float], offset: Int): F[Unit]

  def glUniformMatrix3fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer): F[Unit]

  def glUniformMatrix3fv(location: Int, count: Int, transpose: Boolean, value: Array[Float], offset: Int): F[Unit]

  def glUniformMatrix4fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer): F[Unit]

  def glUniformMatrix4fv(location: Int, count: Int, transpose: Boolean, value: Array[Float], offset: Int): F[Unit]

  def glUseProgram(program: Int): F[Unit]

  def glValidateProgram(program: Int): F[Unit]

  def glVertexAttrib1f(indx: Int, x: Float): F[Unit]

  def glVertexAttrib1fv(indx: Int, values: FloatBuffer): F[Unit]

  def glVertexAttrib2f(indx: Int, x: Float, y: Float): F[Unit]

  def glVertexAttrib2fv(indx: Int, values: FloatBuffer): F[Unit]

  def glVertexAttrib3f(indx: Int, x: Float, y: Float, z: Float): F[Unit]

  def glVertexAttrib3fv(indx: Int, values: FloatBuffer): F[Unit]

  def glVertexAttrib4f(indx: Int, x: Float, y: Float, z: Float, w: Float): F[Unit]

  def glVertexAttrib4fv(indx: Int, values: FloatBuffer): F[Unit]

  def glVertexAttribPointer(indx: Int, size: Int, `type`: Int, normalized: Boolean, stride: Int, ptr: Buffer): F[Unit]

  def glVertexAttribPointer(indx: Int, size: Int, `type`: Int, normalized: Boolean, stride: Int, ptr: Int): F[Unit]
}

object GL20 {
  val GL_ES_VERSION_2_0: Int =  1;
	val GL_DEPTH_BUFFER_BIT: Int =  0x00000100;
	val GL_STENCIL_BUFFER_BIT: Int =  0x00000400;
	val GL_COLOR_BUFFER_BIT: Int =  0x00004000;
	val GL_FALSE: Int =  0;
	val GL_TRUE: Int =  1;
	val GL_POINTS: Int =  0x0000;
	val GL_LINES: Int =  0x0001;
	val GL_LINE_LOOP: Int =  0x0002;
	val GL_LINE_STRIP: Int =  0x0003;
	val GL_TRIANGLES: Int =  0x0004;
	val GL_TRIANGLE_STRIP: Int =  0x0005;
	val GL_TRIANGLE_FAN: Int =  0x0006;
	val GL_ZERO: Int =  0;
	val GL_ONE: Int =  1;
	val GL_SRC_COLOR: Int =  0x0300;
	val GL_ONE_MINUS_SRC_COLOR: Int =  0x0301;
	val GL_SRC_ALPHA: Int =  0x0302;
	val GL_ONE_MINUS_SRC_ALPHA: Int =  0x0303;
	val GL_DST_ALPHA: Int =  0x0304;
	val GL_ONE_MINUS_DST_ALPHA: Int =  0x0305;
	val GL_DST_COLOR: Int =  0x0306;
	val GL_ONE_MINUS_DST_COLOR: Int =  0x0307;
	val GL_SRC_ALPHA_SATURATE: Int =  0x0308;
	val GL_FUNC_ADD: Int =  0x8006;
	val GL_BLEND_EQUATION: Int =  0x8009;
	val GL_BLEND_EQUATION_RGB: Int =  0x8009;
	val GL_BLEND_EQUATION_ALPHA: Int =  0x883D;
	val GL_FUNC_SUBTRACT: Int =  0x800A;
	val GL_FUNC_REVERSE_SUBTRACT: Int =  0x800B;
	val GL_BLEND_DST_RGB: Int =  0x80C8;
	val GL_BLEND_SRC_RGB: Int =  0x80C9;
	val GL_BLEND_DST_ALPHA: Int =  0x80CA;
	val GL_BLEND_SRC_ALPHA: Int =  0x80CB;
	val GL_CONSTANT_COLOR: Int =  0x8001;
	val GL_ONE_MINUS_CONSTANT_COLOR: Int =  0x8002;
	val GL_CONSTANT_ALPHA: Int =  0x8003;
	val GL_ONE_MINUS_CONSTANT_ALPHA: Int =  0x8004;
	val GL_BLEND_COLOR: Int =  0x8005;
	val GL_ARRAY_BUFFER: Int =  0x8892;
	val GL_ELEMENT_ARRAY_BUFFER: Int =  0x8893;
	val GL_ARRAY_BUFFER_BINDING: Int =  0x8894;
	val GL_ELEMENT_ARRAY_BUFFER_BINDING: Int =  0x8895;
	val GL_STREAM_DRAW: Int =  0x88E0;
	val GL_STATIC_DRAW: Int =  0x88E4;
	val GL_DYNAMIC_DRAW: Int =  0x88E8;
	val GL_BUFFER_SIZE: Int =  0x8764;
	val GL_BUFFER_USAGE: Int =  0x8765;
	val GL_CURRENT_VERTEX_ATTRIB: Int =  0x8626;
	val GL_FRONT: Int =  0x0404;
	val GL_BACK: Int =  0x0405;
	val GL_FRONT_AND_BACK: Int =  0x0408;
	val GL_TEXTURE_2D: Int =  0x0DE1;
	val GL_CULL_FACE: Int =  0x0B44;
	val GL_BLEND: Int =  0x0BE2;
	val GL_DITHER: Int =  0x0BD0;
	val GL_STENCIL_TEST: Int =  0x0B90;
	val GL_DEPTH_TEST: Int =  0x0B71;
	val GL_SCISSOR_TEST: Int =  0x0C11;
	val GL_POLYGON_OFFSET_FILL: Int =  0x8037;
	val GL_SAMPLE_ALPHA_TO_COVERAGE: Int =  0x809E;
	val GL_SAMPLE_COVERAGE: Int =  0x80A0;
	val GL_NO_ERROR: Int =  0;
	val GL_INVALID_ENUM: Int =  0x0500;
	val GL_INVALID_VALUE: Int =  0x0501;
	val GL_INVALID_OPERATION: Int =  0x0502;
	val GL_OUT_OF_MEMORY: Int =  0x0505;
	val GL_CW: Int =  0x0900;
	val GL_CCW: Int =  0x0901;
	val GL_LINE_WIDTH: Int =  0x0B21;
	val GL_ALIASED_POINT_SIZE_RANGE: Int =  0x846D;
	val GL_ALIASED_LINE_WIDTH_RANGE: Int =  0x846E;
	val GL_CULL_FACE_MODE: Int =  0x0B45;
	val GL_FRONT_FACE: Int =  0x0B46;
	val GL_DEPTH_RANGE: Int =  0x0B70;
	val GL_DEPTH_WRITEMASK: Int =  0x0B72;
	val GL_DEPTH_CLEAR_VALUE: Int =  0x0B73;
	val GL_DEPTH_FUNC: Int =  0x0B74;
	val GL_STENCIL_CLEAR_VALUE: Int =  0x0B91;
	val GL_STENCIL_FUNC: Int =  0x0B92;
	val GL_STENCIL_FAIL: Int =  0x0B94;
	val GL_STENCIL_PASS_DEPTH_FAIL: Int =  0x0B95;
	val GL_STENCIL_PASS_DEPTH_PASS: Int =  0x0B96;
	val GL_STENCIL_REF: Int =  0x0B97;
	val GL_STENCIL_VALUE_MASK: Int =  0x0B93;
	val GL_STENCIL_WRITEMASK: Int =  0x0B98;
	val GL_STENCIL_BACK_FUNC: Int =  0x8800;
	val GL_STENCIL_BACK_FAIL: Int =  0x8801;
	val GL_STENCIL_BACK_PASS_DEPTH_FAIL: Int =  0x8802;
	val GL_STENCIL_BACK_PASS_DEPTH_PASS: Int =  0x8803;
	val GL_STENCIL_BACK_REF: Int =  0x8CA3;
	val GL_STENCIL_BACK_VALUE_MASK: Int =  0x8CA4;
	val GL_STENCIL_BACK_WRITEMASK: Int =  0x8CA5;
	val GL_VIEWPORT: Int =  0x0BA2;
	val GL_SCISSOR_BOX: Int =  0x0C10;
	val GL_COLOR_CLEAR_VALUE: Int =  0x0C22;
	val GL_COLOR_WRITEMASK: Int =  0x0C23;
	val GL_UNPACK_ALIGNMENT: Int =  0x0CF5;
	val GL_PACK_ALIGNMENT: Int =  0x0D05;
	val GL_MAX_TEXTURE_SIZE: Int =  0x0D33;
	val GL_MAX_TEXTURE_UNITS: Int =  0x84E2;
	val GL_MAX_VIEWPORT_DIMS: Int =  0x0D3A;
	val GL_SUBPIXEL_BITS: Int =  0x0D50;
	val GL_RED_BITS: Int =  0x0D52;
	val GL_GREEN_BITS: Int =  0x0D53;
	val GL_BLUE_BITS: Int =  0x0D54;
	val GL_ALPHA_BITS: Int =  0x0D55;
	val GL_DEPTH_BITS: Int =  0x0D56;
	val GL_STENCIL_BITS: Int =  0x0D57;
	val GL_POLYGON_OFFSET_UNITS: Int =  0x2A00;
	val GL_POLYGON_OFFSET_FACTOR: Int =  0x8038;
	val GL_TEXTURE_BINDING_2D: Int =  0x8069;
	val GL_SAMPLE_BUFFERS: Int =  0x80A8;
	val GL_SAMPLES: Int =  0x80A9;
	val GL_SAMPLE_COVERAGE_VALUE: Int =  0x80AA;
	val GL_SAMPLE_COVERAGE_INVERT: Int =  0x80AB;
	val GL_NUM_COMPRESSED_TEXTURE_FORMATS: Int =  0x86A2;
	val GL_COMPRESSED_TEXTURE_FORMATS: Int =  0x86A3;
	val GL_DONT_CARE: Int =  0x1100;
	val GL_FASTEST: Int =  0x1101;
	val GL_NICEST: Int =  0x1102;
	val GL_GENERATE_MIPMAP: Int =  0x8191;
	val GL_GENERATE_MIPMAP_HINT: Int =  0x8192;
	val GL_BYTE: Int =  0x1400;
	val GL_UNSIGNED_BYTE: Int =  0x1401;
	val GL_SHORT: Int =  0x1402;
	val GL_UNSIGNED_SHORT: Int =  0x1403;
	val GL_INT: Int =  0x1404;
	val GL_UNSIGNED_INT: Int =  0x1405;
	val GL_FLOAT: Int =  0x1406;
	val GL_FIXED: Int =  0x140C;
	val GL_DEPTH_COMPONENT: Int =  0x1902;
	val GL_ALPHA: Int =  0x1906;
	val GL_RGB: Int =  0x1907;
	val GL_RGBA: Int =  0x1908;
	val GL_LUMINANCE: Int =  0x1909;
	val GL_LUMINANCE_ALPHA: Int =  0x190A;
	val GL_UNSIGNED_SHORT_4_4_4_4: Int =  0x8033;
	val GL_UNSIGNED_SHORT_5_5_5_1: Int =  0x8034;
	val GL_UNSIGNED_SHORT_5_6_5: Int =  0x8363;
	val GL_FRAGMENT_SHADER: Int =  0x8B30;
	val GL_VERTEX_SHADER: Int =  0x8B31;
	val GL_MAX_VERTEX_ATTRIBS: Int =  0x8869;
	val GL_MAX_VERTEX_UNIFORM_VECTORS: Int =  0x8DFB;
	val GL_MAX_VARYING_VECTORS: Int =  0x8DFC;
	val GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS: Int =  0x8B4D;
	val GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS: Int =  0x8B4C;
	val GL_MAX_TEXTURE_IMAGE_UNITS: Int =  0x8872;
	val GL_MAX_FRAGMENT_UNIFORM_VECTORS: Int =  0x8DFD;
	val GL_SHADER_TYPE: Int =  0x8B4F;
	val GL_DELETE_STATUS: Int =  0x8B80;
	val GL_LINK_STATUS: Int =  0x8B82;
	val GL_VALIDATE_STATUS: Int =  0x8B83;
	val GL_ATTACHED_SHADERS: Int =  0x8B85;
	val GL_ACTIVE_UNIFORMS: Int =  0x8B86;
	val GL_ACTIVE_UNIFORM_MAX_LENGTH: Int =  0x8B87;
	val GL_ACTIVE_ATTRIBUTES: Int =  0x8B89;
	val GL_ACTIVE_ATTRIBUTE_MAX_LENGTH: Int =  0x8B8A;
	val GL_SHADING_LANGUAGE_VERSION: Int =  0x8B8C;
	val GL_CURRENT_PROGRAM: Int =  0x8B8D;
	val GL_NEVER: Int =  0x0200;
	val GL_LESS: Int =  0x0201;
	val GL_EQUAL: Int =  0x0202;
	val GL_LEQUAL: Int =  0x0203;
	val GL_GREATER: Int =  0x0204;
	val GL_NOTEQUAL: Int =  0x0205;
	val GL_GEQUAL: Int =  0x0206;
	val GL_ALWAYS: Int =  0x0207;
	val GL_KEEP: Int =  0x1E00;
	val GL_REPLACE: Int =  0x1E01;
	val GL_INCR: Int =  0x1E02;
	val GL_DECR: Int =  0x1E03;
	val GL_INVERT: Int =  0x150A;
	val GL_INCR_WRAP: Int =  0x8507;
	val GL_DECR_WRAP: Int =  0x8508;
	val GL_VENDOR: Int =  0x1F00;
	val GL_RENDERER: Int =  0x1F01;
	val GL_VERSION: Int =  0x1F02;
	val GL_EXTENSIONS: Int =  0x1F03;
	val GL_NEAREST: Int =  0x2600;
	val GL_LINEAR: Int =  0x2601;
	val GL_NEAREST_MIPMAP_NEAREST: Int =  0x2700;
	val GL_LINEAR_MIPMAP_NEAREST: Int =  0x2701;
	val GL_NEAREST_MIPMAP_LINEAR: Int =  0x2702;
	val GL_LINEAR_MIPMAP_LINEAR: Int =  0x2703;
	val GL_TEXTURE_MAG_FILTER: Int =  0x2800;
	val GL_TEXTURE_MIN_FILTER: Int =  0x2801;
	val GL_TEXTURE_WRAP_S: Int =  0x2802;
	val GL_TEXTURE_WRAP_T: Int =  0x2803;
	val GL_TEXTURE: Int =  0x1702;
	val GL_TEXTURE_CUBE_MAP: Int =  0x8513;
	val GL_TEXTURE_BINDING_CUBE_MAP: Int =  0x8514;
	val GL_TEXTURE_CUBE_MAP_POSITIVE_X: Int =  0x8515;
	val GL_TEXTURE_CUBE_MAP_NEGATIVE_X: Int =  0x8516;
	val GL_TEXTURE_CUBE_MAP_POSITIVE_Y: Int =  0x8517;
	val GL_TEXTURE_CUBE_MAP_NEGATIVE_Y: Int =  0x8518;
	val GL_TEXTURE_CUBE_MAP_POSITIVE_Z: Int =  0x8519;
	val GL_TEXTURE_CUBE_MAP_NEGATIVE_Z: Int =  0x851A;
	val GL_MAX_CUBE_MAP_TEXTURE_SIZE: Int =  0x851C;
	val GL_TEXTURE0: Int =  0x84C0;
	val GL_TEXTURE1: Int =  0x84C1;
	val GL_TEXTURE2: Int =  0x84C2;
	val GL_TEXTURE3: Int =  0x84C3;
	val GL_TEXTURE4: Int =  0x84C4;
	val GL_TEXTURE5: Int =  0x84C5;
	val GL_TEXTURE6: Int =  0x84C6;
	val GL_TEXTURE7: Int =  0x84C7;
	val GL_TEXTURE8: Int =  0x84C8;
	val GL_TEXTURE9: Int =  0x84C9;
	val GL_TEXTURE10: Int =  0x84CA;
	val GL_TEXTURE11: Int =  0x84CB;
	val GL_TEXTURE12: Int =  0x84CC;
	val GL_TEXTURE13: Int =  0x84CD;
	val GL_TEXTURE14: Int =  0x84CE;
	val GL_TEXTURE15: Int =  0x84CF;
	val GL_TEXTURE16: Int =  0x84D0;
	val GL_TEXTURE17: Int =  0x84D1;
	val GL_TEXTURE18: Int =  0x84D2;
	val GL_TEXTURE19: Int =  0x84D3;
	val GL_TEXTURE20: Int =  0x84D4;
	val GL_TEXTURE21: Int =  0x84D5;
	val GL_TEXTURE22: Int =  0x84D6;
	val GL_TEXTURE23: Int =  0x84D7;
	val GL_TEXTURE24: Int =  0x84D8;
	val GL_TEXTURE25: Int =  0x84D9;
	val GL_TEXTURE26: Int =  0x84DA;
	val GL_TEXTURE27: Int =  0x84DB;
	val GL_TEXTURE28: Int =  0x84DC;
	val GL_TEXTURE29: Int =  0x84DD;
	val GL_TEXTURE30: Int =  0x84DE;
	val GL_TEXTURE31: Int =  0x84DF;
	val GL_ACTIVE_TEXTURE: Int =  0x84E0;
	val GL_REPEAT: Int =  0x2901;
	val GL_CLAMP_TO_EDGE: Int =  0x812F;
	val GL_MIRRORED_REPEAT: Int =  0x8370;
	val GL_FLOAT_VEC2: Int =  0x8B50;
	val GL_FLOAT_VEC3: Int =  0x8B51;
	val GL_FLOAT_VEC4: Int =  0x8B52;
	val GL_INT_VEC2: Int =  0x8B53;
	val GL_INT_VEC3: Int =  0x8B54;
	val GL_INT_VEC4: Int =  0x8B55;
	val GL_BOOL: Int =  0x8B56;
	val GL_BOOL_VEC2: Int =  0x8B57;
	val GL_BOOL_VEC3: Int =  0x8B58;
	val GL_BOOL_VEC4: Int =  0x8B59;
	val GL_FLOAT_MAT2: Int =  0x8B5A;
	val GL_FLOAT_MAT3: Int =  0x8B5B;
	val GL_FLOAT_MAT4: Int =  0x8B5C;
	val GL_SAMPLER_2D: Int =  0x8B5E;
	val GL_SAMPLER_CUBE: Int =  0x8B60;
	val GL_VERTEX_ATTRIB_ARRAY_ENABLED: Int =  0x8622;
	val GL_VERTEX_ATTRIB_ARRAY_SIZE: Int =  0x8623;
	val GL_VERTEX_ATTRIB_ARRAY_STRIDE: Int =  0x8624;
	val GL_VERTEX_ATTRIB_ARRAY_TYPE: Int =  0x8625;
	val GL_VERTEX_ATTRIB_ARRAY_NORMALIZED: Int =  0x886A;
	val GL_VERTEX_ATTRIB_ARRAY_POINTER: Int =  0x8645;
	val GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING: Int =  0x889F;
	val GL_IMPLEMENTATION_COLOR_READ_TYPE: Int =  0x8B9A;
	val GL_IMPLEMENTATION_COLOR_READ_FORMAT: Int =  0x8B9B;
	val GL_COMPILE_STATUS: Int =  0x8B81;
	val GL_INFO_LOG_LENGTH: Int =  0x8B84;
	val GL_SHADER_SOURCE_LENGTH: Int =  0x8B88;
	val GL_SHADER_COMPILER: Int =  0x8DFA;
	val GL_SHADER_BINARY_FORMATS: Int =  0x8DF8;
	val GL_NUM_SHADER_BINARY_FORMATS: Int =  0x8DF9;
	val GL_LOW_FLOAT: Int =  0x8DF0;
	val GL_MEDIUM_FLOAT: Int =  0x8DF1;
	val GL_HIGH_FLOAT: Int =  0x8DF2;
	val GL_LOW_INT: Int =  0x8DF3;
	val GL_MEDIUM_INT: Int =  0x8DF4;
	val GL_HIGH_INT: Int =  0x8DF5;
	val GL_FRAMEBUFFER: Int =  0x8D40;
	val GL_RENDERBUFFER: Int =  0x8D41;
	val GL_RGBA4: Int =  0x8056;
	val GL_RGB5_A1: Int =  0x8057;
	val GL_RGB565: Int =  0x8D62;
	val GL_DEPTH_COMPONENT16: Int =  0x81A5;
	val GL_STENCIL_INDEX: Int =  0x1901;
	val GL_STENCIL_INDEX8: Int =  0x8D48;
	val GL_RENDERBUFFER_WIDTH: Int =  0x8D42;
	val GL_RENDERBUFFER_HEIGHT: Int =  0x8D43;
	val GL_RENDERBUFFER_INTERNAL_FORMAT: Int =  0x8D44;
	val GL_RENDERBUFFER_RED_SIZE: Int =  0x8D50;
	val GL_RENDERBUFFER_GREEN_SIZE: Int =  0x8D51;
	val GL_RENDERBUFFER_BLUE_SIZE: Int =  0x8D52;
	val GL_RENDERBUFFER_ALPHA_SIZE: Int =  0x8D53;
	val GL_RENDERBUFFER_DEPTH_SIZE: Int =  0x8D54;
	val GL_RENDERBUFFER_STENCIL_SIZE: Int =  0x8D55;
	val GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE: Int =  0x8CD0;
	val GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME: Int =  0x8CD1;
	val GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL: Int =  0x8CD2;
	val GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE: Int =  0x8CD3;
	val GL_COLOR_ATTACHMENT0: Int =  0x8CE0;
	val GL_DEPTH_ATTACHMENT: Int =  0x8D00;
	val GL_STENCIL_ATTACHMENT: Int =  0x8D20;
	val GL_NONE: Int =  0;
	val GL_FRAMEBUFFER_COMPLETE: Int =  0x8CD5;
	val GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT: Int =  0x8CD6;
	val GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT: Int =  0x8CD7;
	val GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS: Int =  0x8CD9;
	val GL_FRAMEBUFFER_UNSUPPORTED: Int =  0x8CDD;
	val GL_FRAMEBUFFER_BINDING: Int =  0x8CA6;
	val GL_RENDERBUFFER_BINDING: Int =  0x8CA7;
	val GL_MAX_RENDERBUFFER_SIZE: Int =  0x84E8;
	val GL_INVALID_FRAMEBUFFER_OPERATION: Int =  0x0506;
	val GL_VERTEX_PROGRAM_POINT_SIZE: Int =  0x8642;
	val GL_COVERAGE_BUFFER_BIT_NV: Int =  0x8000;
	val GL_TEXTURE_MAX_ANISOTROPY_EXT: Int =  0x84FE;
	val GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT: Int =  0x84FF;
}
