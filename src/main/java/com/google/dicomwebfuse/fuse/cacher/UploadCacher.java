// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.dicomwebfuse.fuse.cacher;

import com.google.dicomwebfuse.entities.DicomPath;
import com.google.dicomwebfuse.exception.DicomFuseException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;

public class UploadCacher {

  private ConcurrentHashMap<DicomPath, Path> filesForUploading = new ConcurrentHashMap<>();

  public void removePath(DicomPath dicomPath) throws DicomFuseException {
    Path path = filesForUploading.get(dicomPath);
    filesForUploading.remove(dicomPath);
    CacherUtils.deleteFile(path);
  }

  public Path getPath(DicomPath dicomPath) {
    return filesForUploading.get(dicomPath);
  }

  public void createPath(DicomPath dicomPath) throws DicomFuseException {
    Path path = CacherUtils.createTempPath();
    path.toFile().deleteOnExit();
    filesForUploading.put(dicomPath, path);
  }
}
