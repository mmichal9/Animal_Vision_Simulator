#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


//Polaroid Color
mat4 colormatrix = mat4  (1.438,-0.062,-0.062,0.0,
			 			 -0.122, 1.378,-0.122,0.0,
			 			 -0.016,-0.016, 1.483,0.0,
						 -0.030, 0.050,-0.020,0.0);


void mainImage( out vec4 fragColor, in vec2 fragCoord )
{

    vec2 uv = fragCoord.xy;
    vec4 color = texture2D( iChannel0, fragCoord/iResolution.xy );
    mat4 colormatrixDiff = mat4( colormatrix);

    fragColor = colormatrixDiff * color;
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
