#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform vec3                iChannelResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


//Hue shift
mat4 colormatrix = mat4 (-0.3333333,0.6666667,0.6666667,0,
			 			 0.6666667,-0.3333333,0.6666667,0,
			 			 0.6666667,0.6666667,-0.3333333,0,
			 			 0.0, 0.0, 0.0, 1.0);


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
