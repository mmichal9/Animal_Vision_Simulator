#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


//Sepia Color
mat4 colormatrix = mat4 (0.393,0.349,0.272,0,
			 			 0.769,0.686,0.534,0,
			 			 0.189,0.168,0.131,0,
			 			 0.0, 0.0, 0.0, 1);


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
