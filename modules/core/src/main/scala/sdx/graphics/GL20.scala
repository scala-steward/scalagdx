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
