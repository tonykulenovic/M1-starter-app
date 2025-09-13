import { Router } from 'express';

import { upload } from '../config/storage';
import { authenticateToken } from '../middlewares/auth.middleware';
import { MediaController } from '../controllers/media.controller';

const router = Router();
const mediaController = new MediaController();

router.post(
  '/upload',
  authenticateToken,
  upload.single('media'),
  mediaController.uploadImage
);

export default router;
